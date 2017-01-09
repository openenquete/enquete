package am.ik.openenquete.questionnaire;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RepositoryEventHandler
@Component
@RequiredArgsConstructor
public class UsernameHandler {
	private final ContextUsername contextUsername;

	@HandleBeforeCreate
	@HandleBeforeSave
	public void setUsername(UsernameHolder response) {
		response.setUsername(contextUsername.getUsername());
	}
}
