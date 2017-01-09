package am.ik.openenquete.seminar;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

import java.util.*;

import org.springframework.stereotype.Service;

import am.ik.openenquete.questionnaire.enums.Difficulty;
import am.ik.openenquete.questionnaire.enums.Satisfaction;
import am.ik.openenquete.session.ResponseForSessionRepository;
import am.ik.openenquete.session.Summary;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeminarReportService {
	private final ResponseForSessionRepository responseForSessionRepository;

	public Map<Summary.Session, Summary.Report<Satisfaction>> satisfactionReport(
			UUID seminarId) {
		List<Summary<Satisfaction>> summaries = responseForSessionRepository
				.reportBySatisfaction(seminarId);
		return report(summaries);
	}

	public Map<Summary.Session, Summary.Report<Difficulty>> difficultyReport(
			UUID seminarId) {
		List<Summary<Difficulty>> summaries = responseForSessionRepository
				.reportByDifficulty(seminarId);
		return report(summaries);
	}

	<T extends Comparable<T>, S extends Summary<T>> Map<Summary.Session, Summary.Report<T>> report(
			List<S> summaries) {
		return summaries.stream().collect(groupingBy(Summary::asSession, toList()))
				.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> {
					List<S> list = e.getValue();
					long total = list.stream().map(Summary::getTotal)
							.mapToLong(Long::longValue).sum();
					long count = list.stream().map(Summary::getCount)
							.mapToLong(Long::longValue).sum();
					double average = count > 0 ? (double) total / count : 0;
					List<Summary.Detail<T>> details = list.stream().map(Summary::asDetail)
							.sorted(comparing(Summary.Detail::getValue))
							.collect(toList());
					return new Summary.Report<>(average, total, count, details);
				})).entrySet().stream()
				.sorted(Comparator.<Map.Entry<Summary.Session, Summary.Report<T>>> comparingDouble(
						x -> x.getValue().getAverage())
						.thenComparing(x -> x.getValue().getCount()).reversed())
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> v,
						LinkedHashMap::new));
	}
}
