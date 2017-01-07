package jjug.security;

import java.util.Map;
import java.util.Optional;

import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;

import jjug.JjugUser;

@Component
public class JjugPrincipalExtractor implements PrincipalExtractor {
	@Override
	public JjugUser extractPrincipal(Map<String, Object> map) {
		String github = getValue(map, "login");
		String name = getValue(map, "name");
		return JjugUser.builder().github(github).name(name.isEmpty() ? github : name)
				.email(getValue(map, "email")).avatarUrl(getValue(map, "avatar_url"))
				.build();
	}

	private String getValue(Map<String, Object> map, String key) {
		return Optional.ofNullable(map.get(key)).map(Object::toString).orElse("");
	}
}
