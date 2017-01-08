package jjug.admin;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jjug.questionnaire.enums.Difficulty;
import jjug.questionnaire.enums.Satisfaction;
import jjug.seminar.Seminar;
import jjug.seminar.SeminarReportService;
import jjug.seminar.SeminarRepository;
import jjug.session.Summary;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReportController {
	private final SeminarRepository seminarRepository;
	private final SeminarReportService seminarReportService;

	@GetMapping("admin/seminars/{seminarId}/report")
	String report(@PathVariable UUID seminarId, Model model, Locale locale)
			throws IOException {
		Seminar seminar = seminarRepository.findOne(seminarId).get();

		Map<Summary.Session, Summary.Report<Satisfaction>> satisfactionReport = seminarReportService
				.satisfactionReport(seminarId);
		Map<Summary.Session, Summary.Report<Difficulty>> difficultyReport = seminarReportService
				.difficultyReport(seminarId);

		model.addAttribute("seminar", seminar);
		model.addAttribute("satisfactionReport", satisfactionReport);
		model.addAttribute("difficultyReport", difficultyReport);
		return "admin/report";
	}
}
