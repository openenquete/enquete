package am.ik.openenquete.security;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;

import am.ik.openenquete.EnqueteUser;

@Component
public class EnquetePrincipalExtractor implements PrincipalExtractor {
	@Override
	public EnqueteUser extractPrincipal(Map<String, Object> map) {
		String github = getValue(map, "login");
		String name = getValue(map, "name");
		return EnqueteUser.builder().github(github).name(name.isEmpty() ? github : name)
				.email(getValue(map, "email")).avatarUrl(getValue(map, "avatar_url"))
				.build();
	}

	private String getValue(Map<String, Object> map, String key) {
		return Optional.ofNullable(map.get(key)).map(Object::toString).orElse("");
	}
}
