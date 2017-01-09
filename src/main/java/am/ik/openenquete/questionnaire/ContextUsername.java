package am.ik.openenquete.questionnaire;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import am.ik.openenquete.EnqueteUser;

@Component
public class ContextUsername {
	public String getUsername() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof EnqueteUser) {
			EnqueteUser user = EnqueteUser.class.cast(authentication.getPrincipal());
			return user.getGithub();
		}
		else {
			// use HTTP SessionId instead of GitHub username
			return RequestContextHolder.currentRequestAttributes().getSessionId();
		}
	}
}
