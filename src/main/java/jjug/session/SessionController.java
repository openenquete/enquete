package jjug.session;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jjug.questionnaire.ContextUsername;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SessionController {
	private final SessionRepository sessionRepository;
	private final ResponseForSessionRepository responseForSessionRepository;
	private final ContextUsername contextUsername;

	@GetMapping("sessions/{sessionId}")
	String session(@PathVariable UUID sessionId, Model model) {
		Session session = sessionRepository.findOne(sessionId).get();
		Optional<ResponseForSession> response = responseForSessionRepository
				.findBySession_SessionIdAndUsername(sessionId,
						contextUsername.getUsername());
		model.addAttribute("s", session);
		model.addAttribute("submitted", response.isPresent());
		return "session";
	}
}
