package jjug.seminar;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

public interface SeminarRepository extends Repository<Seminar, UUID> {
	Optional<Seminar> findOne(UUID id);

	Page<Seminar> findAll(Pageable pageable);

	Page<Seminar> findBySeminarClosedIsNull(Pageable pageable);

	Seminar save(Seminar seminar);
}
