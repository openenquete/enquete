package am.ik.openenquete.session;

import am.ik.openenquete.seminar.Seminar;
import am.ik.openenquete.seminar.SeminarRepository;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(ResponseForSession.class)
@Component
public class ResponseForSessionHandler {
	private final SeminarRepository seminarRepository;

	public ResponseForSessionHandler(SeminarRepository seminarRepository) {
		this.seminarRepository = seminarRepository;
	}

	@HandleBeforeCreate
	@HandleBeforeSave
	public void check(ResponseForSession response) {
		Session session = response.getSession(); // must not be null
		Seminar seminar = seminarRepository.findBySessions(session).get(); // NoSuchElementException
																			// => 404
		if (!seminar.isOpen()) {
			throw new IllegalStateException("The seminar has been closed.");
		}
	}
}
