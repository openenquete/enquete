package jjug.admin;

import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	private final ObjectMapper objectMapper;
	private final MessageSource messageSource;

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
		model.addAttribute("satisfactionsArray",
				arrayJsonString(satisfactionReport, Satisfaction.class, locale));
		model.addAttribute("difficultiesArray",
				arrayJsonString(difficultyReport, Difficulty.class, locale));
		return "admin/report";
	}

	<T extends Comparable<T>> String arrayJsonString(
			Map<Summary.Session, Summary.Report<T>> map, Class<? extends Enum> clazz,
			Locale locale) throws IOException {
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

}
