package am.ik.openenquete.seminar;

import am.ik.openenquete.questionnaire.enums.Difficulty;
import am.ik.openenquete.questionnaire.enums.Satisfaction;
import am.ik.openenquete.session.ResponseForSessionRepository;
import am.ik.openenquete.session.Summary;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Service
public class SeminarReportService {
	private final ResponseForSessionRepository responseForSessionRepository;

	public SeminarReportService(ResponseForSessionRepository responseForSessionRepository) {
		this.responseForSessionRepository = responseForSessionRepository;
	}

	private Map<Summary.Session, Summary.SatisfactionReport> satisfactionReport(
			Supplier<List<Summary<Satisfaction>>> supplier) {
		List<Summary<Satisfaction>> summaries = supplier.get();
		Map<Summary.Session, Summary.Report<Satisfaction>> report = report(summaries);
		return report.entrySet().stream()
				.collect(toMap(Map.Entry::getKey,
						e -> new Summary.SatisfactionReport(e.getValue())))
				.entrySet().stream()
				.sorted(Comparator.<Map.Entry<Summary.Session, Summary.SatisfactionReport>>comparingDouble(
						x -> x.getValue().getNsat())
						.thenComparing(x -> x.getValue().getCount())
						.thenComparing(x -> x.getValue().getAverage()).reversed())
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> v,
						LinkedHashMap::new));
	}

	private Map<Summary.Session, Summary.Report<Difficulty>> difficultyReport(
			Supplier<List<Summary<Difficulty>>> supplier) {
		List<Summary<Difficulty>> summaries = supplier.get();
		return report(summaries);
	}

	public Map<Summary.Session, Summary.SatisfactionReport> satisfactionReport(
			UUID seminarId) {
		return this.satisfactionReport(
				() -> responseForSessionRepository.reportBySatisfaction(seminarId));
	}

	public Map<Summary.Session, Summary.Report<Difficulty>> difficultyReport(
			UUID seminarId) {
		return this.difficultyReport(
				() -> responseForSessionRepository.reportByDifficulty(seminarId));
	}

	public Map<Summary.Session, Summary.SatisfactionReport> satisfactionReportAll() {
		return this.satisfactionReport(
				responseForSessionRepository::reportBySatisfactionAll);
	}

	public Map<Summary.Session, Summary.Report<Difficulty>> difficultyReportAll() {
		return this.difficultyReport(responseForSessionRepository::reportByDifficultyAll);
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
				.sorted(Comparator.<Map.Entry<Summary.Session, Summary.Report<T>>>comparingDouble(
						x -> x.getValue().getAverage())
						.thenComparing(x -> x.getValue().getCount()).reversed())
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> v,
						LinkedHashMap::new));
	}
}
