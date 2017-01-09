package am.ik.openenquete.seminar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import am.ik.openenquete.qrcode.QrCode;
import am.ik.openenquete.questionnaire.ContextUsername;
import am.ik.openenquete.session.Session;
import am.ik.openenquete.urlshortener.UrlShortenerClient;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SeminarController {
	private final SeminarRepository seminarRepository;
	private final ResponseForSeminarRepository responseForSeminarRepository;
	private final ContextUsername contextUsername;
	private final Optional<UrlShortenerClient> urlShortenerClient;
	private final Optional<QrCode> qrCode;

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
		qrCode.ifPresent(code -> model.addAttribute("qrCode", code.dataUrl(requestURL)));
		model.addAttribute("seminar", seminar);
		model.addAttribute("sessions", sessions);
		model.addAttribute("submitted", response.isPresent());
		return "seminar";
	}
}
