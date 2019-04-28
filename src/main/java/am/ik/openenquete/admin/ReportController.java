package am.ik.openenquete.admin;

import am.ik.openenquete.questionnaire.enums.Difficulty;
import am.ik.openenquete.questionnaire.enums.Satisfaction;
import am.ik.openenquete.seminar.Seminar;
import am.ik.openenquete.seminar.SeminarReportService;
import am.ik.openenquete.seminar.SeminarRepository;
import am.ik.openenquete.session.Summary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Controller
public class ReportController {
	private final SeminarRepository seminarRepository;
	private final SeminarReportService seminarReportService;
	private final ObjectMapper objectMapper;
	private final MessageSource messageSource;

	public ReportController(SeminarRepository seminarRepository, SeminarReportService seminarReportService, ObjectMapper objectMapper, MessageSource messageSource) {
		this.seminarRepository = seminarRepository;
		this.seminarReportService = seminarReportService;
		this.objectMapper = objectMapper;
		this.messageSource = messageSource;
	}

	@GetMapping("admin/seminars/{seminarId}/report")
	String report(@PathVariable UUID seminarId, Model model, Locale locale)
			throws IOException {
		Seminar seminar = seminarRepository.findOne(seminarId).get();

		Map<Summary.Session, Summary.SatisfactionReport> satisfactionReport = seminarReportService
				.satisfactionReport(seminarId);

		Map<Summary.Session, Summary.Report<Difficulty>> difficultyReport = seminarReportService
				.difficultyReport(seminarId);

		model.addAttribute("seminar", seminar);
		model.addAttribute("satisfactionReport", satisfactionReport);
		model.addAttribute("difficultyReport", difficultyReport);
		model.addAttribute("satisfactionsArray",
				arrayJsonString(satisfactionReport, Satisfaction.class, locale));
		model.addAttribute("difficultiesArray",
				arrayJsonString(difficultyReport, Difficulty.class, locale));
		model.addAttribute("correlationData", objectMapper.writeValueAsString(
				correlationData(satisfactionReport, difficultyReport)));
		return "admin/report";
	}

	@GetMapping("admin/seminars/report")
	String report(Model model, Locale locale) throws IOException {
		Map<Summary.Session, Summary.SatisfactionReport> satisfactionReport = seminarReportService
				.satisfactionReportAll();

		Map<Summary.Session, Summary.Report<Difficulty>> difficultyReport = seminarReportService
				.difficultyReportAll();

		model.addAttribute("satisfactionReport", satisfactionReport);
		model.addAttribute("difficultyReport", difficultyReport);
		model.addAttribute("satisfactionsArray",
				arrayJsonString(satisfactionReport, Satisfaction.class, locale));
		model.addAttribute("difficultiesArray",
				arrayJsonString(difficultyReport, Difficulty.class, locale));
		model.addAttribute("correlationData", objectMapper.writeValueAsString(
				correlationData(satisfactionReport, difficultyReport)));
		return "admin/reportAll";
	}

	<T extends Comparable<T>> String arrayJsonString(
			Map<Summary.Session, ? extends Summary.Report<T>> map,
			Class<? extends Enum> clazz, Locale locale) throws IOException {
		List<Object[]> dataList = new ArrayList<>();
		int len = clazz.getEnumConstants().length;
		dataList.add(labels(clazz, locale));
		map.forEach((session, report) -> {
			Object[] data = new Object[len + 1];
			data[0] = session.getSessionName();
			Map<Object, Double> dataMap = initMap(clazz);
			long count = report.getCount();
			report.getDetails().forEach(d -> {
				dataMap.put(d.getValue(), (double) d.getCount() * 100 / count);
			});
			int i = 1;
			Iterator<Double> iterator = dataMap.values().iterator();
			while (iterator.hasNext() && i <= len) {
				data[i++] = iterator.next();
			}
			dataList.add(data);
		});
		return objectMapper.writeValueAsString(dataList);
	}

	String[] labels(Class<? extends Enum> clazz, Locale locale) {
		Enum[] values = clazz.getEnumConstants();
		String[] labels = new String[values.length + 1];
		labels[0] = "Session";
		for (int i = 0; i < values.length; i++) {
			labels[i + 1] = messageSource.getMessage(clazz.getSimpleName().toLowerCase()
					+ "." + values[i].name().toLowerCase(), null, locale);
		}
		return labels;
	}

	<T extends Enum> Map<Object, Double> initMap(Class<T> clazz) {
		Map<Object, Double> map = new LinkedHashMap<>();
		Stream.of(clazz.getEnumConstants()).forEach(e -> map.put(e, 0.0));
		return map;
	}

	List<Object[]> correlationData(
			Map<Summary.Session, Summary.SatisfactionReport> satisfactionReport,
			Map<Summary.Session, Summary.Report<Difficulty>> difficultyReport) {
		List<Object[]> data = new ArrayList<>();
		data.add(new String[] { "満足度", "難易度" });
		satisfactionReport.forEach((session, satisfaction) -> {
			Summary.Report<Difficulty> difficulty = difficultyReport.get(session);
			if (difficulty != null) {
				data.add(
						new Object[] { satisfaction.getNsat(), difficulty.getAverage() });
			}
		});
		return data;
	}

}
