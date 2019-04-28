package am.ik.openenquete.session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;

public interface SessionRepository extends Repository<Session, UUID> {

	Optional<Session> findBySessionId(UUID id);

	List<Session> findAll();

	Session save(Session session);

	void deleteBySessionId(UUID id);

	List<Session> findBySpeakers(String speaker);
}
