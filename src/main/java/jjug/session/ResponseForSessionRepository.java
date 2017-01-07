package jjug.session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

@RestResource(path = "responses_for_session", rel = "responses_for_session")
public interface ResponseForSessionRepository
		extends Repository<ResponseForSession, UUID> {
	@RestResource(exported = false)
	@PreAuthorize("hasRole('ADMIN') or @sessionRepository.findOne(#sessionId)?.get()?.speakers?.contains(principal?.github)")
	List<ResponseForSession> findBySession_SessionId(@Param("sessionId") UUID sessionId);

	@RestResource(exported = false)
	Optional<ResponseForSession> findBySession_SessionIdAndUsername(
			@Param("sessionId") UUID sessionId, @Param("username") String username);

	ResponseForSession save(ResponseForSession responseForSession);
}
