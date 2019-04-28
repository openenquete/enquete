package am.ik.openenquete.seminar;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import am.ik.openenquete.session.Session;

public interface SeminarRepository extends Repository<Seminar, UUID> {
	Optional<Seminar> findBySeminarId(UUID id);

	Optional<Seminar> findBySessions(@Param("sessions") Session session);

	Page<Seminar> findAll(Pageable pageable);

	Page<Seminar> findBySeminarClosedIsNull(Pageable pageable);

	Seminar save(Seminar seminar);

	long count();
}
