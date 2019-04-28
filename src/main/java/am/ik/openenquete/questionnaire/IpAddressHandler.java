package am.ik.openenquete.questionnaire;

import org.slf4j.Logger;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;

@RepositoryEventHandler
@Component
public class IpAddressHandler {
	static final String X_FORWARDED_FOR = "X-Forwarded-For";

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(IpAddressHandler.class);

	private final HttpServletRequest request;

	public IpAddressHandler(HttpServletRequest request) {
		this.request = request;
	}

	@HandleBeforeCreate
	@HandleBeforeSave
	public void befoer(IpAddressHolder response) {
		ServletServerHttpRequest req = new ServletServerHttpRequest(request);
		if (log.isDebugEnabled()) {
			log.debug("X-Forwarded-For: {}", req.getHeaders().get(X_FORWARDED_FOR));
			log.debug("RemoteAddress  : {}", req.getRemoteAddress());
		}
		response.setIpAddress(getIpAddress(req));
	}

	String getIpAddress(ServletServerHttpRequest req) {
		if (CollectionUtils.isEmpty(req.getHeaders().get(X_FORWARDED_FOR))) {
			return req.getRemoteAddress().getAddress().getHostAddress();
		}
		else {
			return req.getHeaders().getFirst(X_FORWARDED_FOR);
		}
	}
}
