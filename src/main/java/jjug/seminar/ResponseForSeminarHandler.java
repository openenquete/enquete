package jjug.seminar;

import java.util.Objects;

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
		if (!Objects.isNull(response.getSeminar().getSeminarClosed())) {
			throw new IllegalStateException("The seminar has been closed.");
		}
	}
}
