package am.ik.openenquete.questionnaire;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@RepositoryEventHandler
@Component
public class UsernameHandler {
	private final ContextUsername contextUsername;

	public UsernameHandler(ContextUsername contextUsername) {
		this.contextUsername = contextUsername;
	}

	@HandleBeforeCreate
	@HandleBeforeSave
	public void setUsername(UsernameHolder response) {
		response.setUsername(contextUsername.getUsername());
	}
}
