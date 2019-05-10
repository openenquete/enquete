package am.ik.openenquete.session;

import am.ik.openenquete.EnqueteProps;
import am.ik.openenquete.seminar.Seminar;
import am.ik.openenquete.seminar.SeminarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RepositoryEventHandler(ResponseForSession.class)
@Component
public class ResponseForSessionHandler {

    private final SeminarRepository seminarRepository;

    private final SessionRepository sessionRepository;

    private final RestTemplate restTemplate;

    private final EnqueteProps props;

    private final Logger log = LoggerFactory.getLogger(ResponseForSessionHandler.class);

    public ResponseForSessionHandler(SeminarRepository seminarRepository, SessionRepository sessionRepository, RestTemplateBuilder builder, EnqueteProps props) {
        this.seminarRepository = seminarRepository;
        this.sessionRepository = sessionRepository;
        this.restTemplate = builder.build();
        this.props = props;
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    public void check(ResponseForSession response) {
        Session session = response.getSession(); // must not be null
        Seminar seminar = seminarRepository.findBySessions(session).get(); // NoSuchElementException
        // => 404
        if (!seminar.isOpen()) {
            throw new IllegalStateException("The seminar has been closed.");
        }
    }

    @HandleAfterCreate
    public void webhook(ResponseForSession response) {
        if (!StringUtils.isEmpty(this.props.getResponseForSessionWebhookUrl())) {
            UUID sessionId = response.getSession().getSessionId();
            this.sessionRepository.findBySessionId(sessionId)
                .ifPresent(session -> {
                    Map<String, Object> body = new HashMap<>();
                    body.put("sessionId", sessionId.toString());
                    body.put("sessionName", session.getSessionName());
                    body.put("satisfaction", response.getSatisfaction().name());
                    body.put("count", 1);
                    RequestEntity<Map<String, Object>> requestEntity = RequestEntity.post(URI.create(this.props.getResponseForSessionWebhookUrl() + "/webhook"))
                        .body(body);
                    try {
                        this.restTemplate.exchange(requestEntity, String.class);
                    } catch (RuntimeException e) {
                        log.warn("Failed to push a webhook", e);
                    }
                });
        }
    }
}
