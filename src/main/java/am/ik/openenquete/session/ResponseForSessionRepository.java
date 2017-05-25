package am.ik.openenquete.session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import am.ik.openenquete.questionnaire.enums.Difficulty;
import am.ik.openenquete.questionnaire.enums.Satisfaction;

@RestResource(path = "responses_for_session", rel = "responses_for_session")
public interface ResponseForSessionRepository
		extends Repository<ResponseForSession, UUID> {
	@RestResource(exported = false)
	@PreAuthorize("hasRole('ADMIN') or @sessionRepository.findOne(#sessionId)?.get()?.speakers?.contains(principal?.github)")
	List<ResponseForSession> findBySession_SessionId(@Param("sessionId") UUID sessionId);

	@RestResource(exported = false)
	@PreAuthorize("hasRole('ADMIN')")
	List<ResponseForSession> findBySession_Seminar_SeminarId(@Param("seminarId") UUID seminarId);

	@RestResource(exported = false)
	Optional<ResponseForSession> findBySession_SessionIdAndUsername(
			@Param("sessionId") UUID sessionId, @Param("username") String username);

	ResponseForSession save(ResponseForSession responseForSession);

	@Query("SELECT s.sessionId AS sessionId, s.sessionName AS sessionName, x.satisfaction AS value, COUNT(x.satisfaction) AS count, COUNT(x.satisfaction) * x.satisfaction AS total, x.session.seminar.seminarId AS seminarId FROM ResponseForSession x JOIN x.session s GROUP BY s.sessionId, x.satisfaction HAVING x.session.seminar.seminarId = :seminarId")
	List<Summary<Satisfaction>> reportBySatisfaction(@Param("seminarId") UUID seminarId);

	@Query("SELECT s.sessionId AS sessionId, s.sessionName AS sessionName, x.difficulty AS value, COUNT(x.difficulty) AS count, COUNT(x.difficulty) * x.difficulty AS total, x.session.seminar.seminarId AS seminarId FROM ResponseForSession x JOIN x.session s GROUP BY s.sessionId, x.difficulty HAVING x.session.seminar.seminarId = :seminarId")
	List<Summary<Difficulty>> reportByDifficulty(@Param("seminarId") UUID seminarId);
}
