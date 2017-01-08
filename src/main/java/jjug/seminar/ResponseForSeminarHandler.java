package jjug.seminar;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(ResponseForSeminar.class)
@Component
public class ResponseForSeminarHandler {

	@HandleBeforeCreate
	@HandleBeforeSave
	public void check(ResponseForSeminar response) {
		if (!response.getSeminar().isOpen()) {
			throw new IllegalStateException("The seminar has been closed.");
		}
	}
}
