package jjug.session;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jjug.qrcode.QrCode;
import jjug.questionnaire.ContextUsername;
import jjug.urlshortener.UrlShortenerClient;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SessionController {
	private final SessionRepository sessionRepository;
	private final ResponseForSessionRepository responseForSessionRepository;
	private final ContextUsername contextUsername;
	private final Optional<UrlShortenerClient> urlShortenerClient;
	private final Optional<QrCode> qrCode;

	@GetMapping("sessions/{sessionId}")
	String session(@PathVariable UUID sessionId, Model model,
			@Value("#{request.requestURL}") String requestURL) {
		Session session = sessionRepository.findOne(sessionId).get();
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
