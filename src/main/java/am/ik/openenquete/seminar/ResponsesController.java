package am.ik.openenquete.seminar;

import am.ik.openenquete.session.ResponseForSessionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ResponsesController {

    private final ResponseForSeminarRepository responseForSeminarRepository;

    private final ResponseForSessionRepository responseForSessionRepository;

    public ResponsesController(ResponseForSeminarRepository responseForSeminarRepository, ResponseForSessionRepository responseForSessionRepository) {
        this.responseForSeminarRepository = responseForSeminarRepository;
        this.responseForSessionRepository = responseForSessionRepository;
    }

    @Transactional
    @DeleteMapping(path = "/v1/seminars/{seminarId}/responses")
    public void reset(@PathVariable("seminarId") UUID seminarId) {
        this.responseForSessionRepository.deleteBySession_Seminar_SeminarId(seminarId);
        this.responseForSeminarRepository.deleteBySeminar_SeminarId(seminarId);
    }
}
