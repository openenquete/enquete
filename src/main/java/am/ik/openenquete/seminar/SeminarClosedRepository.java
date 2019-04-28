package am.ik.openenquete.seminar;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

@RestResource(path = "seminar_closed", rel = "seminar_closed")
public interface SeminarClosedRepository extends Repository<SeminarClosed, UUID> {

	Optional<SeminarClosed> findByClosedId(UUID id);

	SeminarClosed save(SeminarClosed closed);

	default void delete(@Param("closedId") UUID closedId) {
		findByClosedId(closedId).ifPresent(closed -> closed.getSeminar().setSeminarClosed(null));
	}
}
