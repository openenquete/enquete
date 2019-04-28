package am.ik.openenquete.seminar;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "seminar_closed")
public interface SeminarClosedRepository extends CrudRepository<SeminarClosed, UUID> {
	Optional<SeminarClosed> findByClosedId(UUID id);

	SeminarClosed save(SeminarClosed closed);

	default void delete(SeminarClosed c) {
		findByClosedId(c.getClosedId()).ifPresent(closed -> closed.getSeminar().setSeminarClosed(null));
	}
}
