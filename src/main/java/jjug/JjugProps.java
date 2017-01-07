package jjug;

import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "jjug")
@Data
@Component
public class JjugProps {
	private Set<String> adminUsers;
}
