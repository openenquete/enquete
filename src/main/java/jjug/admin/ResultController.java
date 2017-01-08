package jjug.admin;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.ObjectMapper;

import jjug.questionnaire.enums.Difficulty;
import jjug.questionnaire.enums.Satisfaction;
import jjug.seminar.ResponseForSeminar;
import jjug.seminar.ResponseForSeminarRepository;
import jjug.seminar.Seminar;
import jjug.seminar.SeminarRepository;
import jjug.session.ResponseForSession;
import jjug.session.ResponseForSessionRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ResultController {

	private final SeminarRepository seminarRepository;
	private final ResponseForSeminarRepository responseForSeminarRepository;
	private final ResponseForSessionRepository responseForSessionRepository;
	private final MessageSource messageSource;
	private final ObjectMapper objectMapper;

	@GetMapping("admin/seminars/{seminarId}/result")
	String seminarForAdmin(@PathVariable UUID seminarId, Model model, Locale locale)
			throws IOException {
		List<ResponseForSeminar> responses = responseForSeminarRepository
				.findBySeminar_SeminarId(seminarId);
		Seminar seminar = seminarRepository.findOne(seminarId).get();
		Map<String, Long> satisfactions = satisfactionMap(
				responses.stream().collect(
						groupingBy(ResponseForSeminar::getSatisfaction, counting())),
				locale);

		List<String> comments = responses.stream().map(ResponseForSeminar::getComment)
				.filter(s -> !s.isEmpty()).collect(toList());
		List<String> requests = responses.stream().map(ResponseForSeminar::getRequest)
				.filter(s -> !s.isEmpty()).collect(toList());

		model.addAttribute("seminar", seminar);
		model.addAttribute("satisfactions", satisfactions);
		model.addAttribute("satisfactionsArray",
				arrayJsonString(satisfactions, "Satisfaction"));
		model.addAttribute("comments", comments);
		model.addAttribute("requests", requests);
		return "admin/seminar";
	}

	@GetMapping({ "admin/sessions/{sessionId}/result", "speaker/sessions/{sessionId}" })
	String sessionForAdmin(@PathVariable UUID sessionId, Model model, Locale locale)
			throws IOException {
		List<ResponseForSession> responses = responseForSessionRepository
				.findBySession_SessionId(sessionId);

		Map<String, Long> satisfactions = satisfactionMap(
				responses.stream().collect(
						groupingBy(ResponseForSession::getSatisfaction, counting())),
				locale);
		Map<String, Long> difficulties = difficultyMap(
				responses.stream().collect(
						groupingBy(ResponseForSession::getDifficulty, counting())),
				locale);
		List<String> comments = responses.stream().map(ResponseForSession::getComment)
				.filter(s -> !s.isEmpty()).collect(toList());

		responses.stream().map(ResponseForSession::getSession).findAny()
				.ifPresent(session -> model.addAttribute("s", session));
		model.addAttribute("satisfactions", satisfactions);
		model.addAttribute("satisfactionsArray",
				arrayJsonString(satisfactions, "Satisfaction"));
		model.addAttribute("difficulties", difficulties);
		model.addAttribute("difficultiesArray",
				arrayJsonString(difficulties, "Difficulty"));
		model.addAttribute("comments", comments);
		return "admin/session";
	}

	String arrayJsonString(Map<String, Long> map, String label) throws IOException {
		return objectMapper.writeValueAsString(map.entrySet().stream()
				.map(e -> new Object[] { e.getKey(), e.getValue() })
				.collect(toCollection(() -> {
					List<Object[]> result = new ArrayList<>();
					result.add(new Object[] { label, "Count" });
					return result;
				})));
	}

	Map<String, Long> satisfactionMap(Map<Satisfaction, Long> map, Locale locale) {
		Map<Satisfaction, Long> satisfactions = Stream.of(Satisfaction.values()).collect(
				toMap(Function.identity(), e -> 0L, (k, v) -> v, LinkedHashMap::new));
		map.forEach(satisfactions::put);
		return satisfactions.entrySet().stream()
				.collect(toMap(e -> satisfaction(e.getKey(), locale), Map.Entry::getValue,
						(k, v) -> v, LinkedHashMap::new));
	}

	Map<String, Long> difficultyMap(Map<Difficulty, Long> map, Locale locale) {
		Map<Difficulty, Long> difficulties = Stream.of(Difficulty.values()).collect(
				toMap(Function.identity(), e -> 0L, (k, v) -> v, LinkedHashMap::new));
		map.forEach(difficulties::put);
		return difficulties.entrySet().stream()
				.collect(toMap(e -> difficulty(e.getKey(), locale), Map.Entry::getValue,
						(k, v) -> v, LinkedHashMap::new));
	}

	String satisfaction(Satisfaction satisfaction, Locale locale) {
		return messageSource.getMessage(
				"satisfaction." + satisfaction.name().toLowerCase(), null, locale);
	}

	String difficulty(Difficulty difficulty, Locale locale) {
		return messageSource.getMessage("difficulty." + difficulty.name().toLowerCase(),
				null, locale);
	}
}
