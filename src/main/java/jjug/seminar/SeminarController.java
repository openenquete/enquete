package jjug.seminar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jjug.questionnaire.ContextUsername;
import jjug.session.Session;
import jjug.urlshortener.UrlShortenerClient;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SeminarController {
	private final SeminarRepository seminarRepository;
	private final ResponseForSeminarRepository responseForSeminarRepository;
	private final ContextUsername contextUsername;
	private final Optional<UrlShortenerClient> urlShortenerClient;

	@GetMapping("seminars/{seminarId}")
	String list(@PathVariable UUID seminarId, Model model,
			@Value("#{request.requestURL}") String requestURL) {
		Seminar seminar = seminarRepository.findOne(seminarId).get();
		List<Session> sessions = seminar.getSessions();
		Optional<ResponseForSeminar> response = responseForSeminarRepository
				.findBySeminar_SeminarIdAndUsername(seminarId,
						contextUsername.getUsername());
		urlShortenerClient.ifPresent(client -> {
			String shortenUrl = client.shorten(requestURL);
			model.addAttribute("shortenUrl", shortenUrl);
		});
		model.addAttribute("seminar", seminar);
		model.addAttribute("sessions", sessions);
		model.addAttribute("submitted", response.isPresent());
		return "seminar";
	}
}
