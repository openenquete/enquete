package jjug.seminar;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(path = "responses_for_seminar", rel = "responses_for_seminar")
public interface ResponseForSeminarRepository
		extends Repository<ResponseForSeminar, UUID> {

	@RestResource(exported = false)
	List<ResponseForSeminar> findBySeminar_SeminarId(@Param("seminarId") UUID seminarId);

	@RestResource(exported = false)
	Optional<ResponseForSeminar> findBySeminar_SeminarIdAndUsername(
			@Param("seminarId") UUID seminarId, @Param("username") String username);

	ResponseForSeminar save(ResponseForSeminar responseForSeminar);
}
