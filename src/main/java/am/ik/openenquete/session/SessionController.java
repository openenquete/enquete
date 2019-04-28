package am.ik.openenquete.session;

import am.ik.openenquete.qrcode.QrCode;
import am.ik.openenquete.questionnaire.ContextUsername;
import am.ik.openenquete.urlshortener.UrlShortenerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@Controller
public class SessionController {
	private final SessionRepository sessionRepository;
	private final ResponseForSessionRepository responseForSessionRepository;
	private final ContextUsername contextUsername;
	private final Optional<UrlShortenerClient> urlShortenerClient;
	private final Optional<QrCode> qrCode;

	public SessionController(SessionRepository sessionRepository, ResponseForSessionRepository responseForSessionRepository, ContextUsername contextUsername,
							 Optional<UrlShortenerClient> urlShortenerClient, Optional<QrCode> qrCode) {
		this.sessionRepository = sessionRepository;
		this.responseForSessionRepository = responseForSessionRepository;
		this.contextUsername = contextUsername;
		this.urlShortenerClient = urlShortenerClient;
		this.qrCode = qrCode;
	}

	@GetMapping("sessions/{sessionId}")
	String session(@PathVariable UUID sessionId, Model model,
			@Value("#{request.requestURL}") String requestURL) {
		Session session = sessionRepository.findBySessionId(sessionId).get();
		Optional<ResponseForSession> response = responseForSessionRepository
				.findBySession_SessionIdAndUsername(sessionId,
						contextUsername.getUsername());
		urlShortenerClient.ifPresent(client -> {
			String shortenUrl = client.shorten(requestURL);
			model.addAttribute("shortenUrl", shortenUrl);
		});
		qrCode.ifPresent(code -> model.addAttribute("qrCode", code.dataUrl(requestURL)));
		model.addAttribute("s", session);
		model.addAttribute("submitted", response.isPresent());
		return "session";
	}
}
