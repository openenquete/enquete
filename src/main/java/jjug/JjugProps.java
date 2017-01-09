package jjug;

import java.util.Set;

import javax.validation.constraints.Min;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "jjug")
@Data
@Component
public class JjugProps {
	private Set<String> adminUsers;
	private Bitly bitly = new Bitly();
	private QrCode qrCode = new QrCode();

	@Data
	public static class Bitly {
		private String accessToken;
	}

	@Data
	public static class QrCode {
		private boolean enabled = true;
		@Min(32)
		private int size = 240;
	}
}
