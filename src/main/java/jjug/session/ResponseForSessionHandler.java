package jjug.session;

import java.util.Objects;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@RepositoryEventHandler(ResponseForSession.class)
@Component
public class ResponseForSessionHandler {

	@HandleBeforeCreate
	@HandleBeforeSave
	public void check(ResponseForSession response) {
		if (!Objects.isNull(response.getSession().getSeminar().getSeminarClosed())) {
			throw new IllegalStateException("The seminar has been closed.");
		}
	}
}
