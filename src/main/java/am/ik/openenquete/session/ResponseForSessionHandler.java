package am.ik.openenquete.session;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import am.ik.openenquete.seminar.Seminar;
import am.ik.openenquete.seminar.SeminarRepository;
import lombok.RequiredArgsConstructor;

@RepositoryEventHandler(ResponseForSession.class)
@Component
@RequiredArgsConstructor
public class ResponseForSessionHandler {
	private final SeminarRepository seminarRepository;

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
