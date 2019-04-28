package am.ik.openenquete.urlshortener;

import am.ik.openenquete.EnqueteProps;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@ConditionalOnProperty(name = "enquete.googl.api-key")
@Component
@CacheConfig(cacheNames = "urlshortener")
public class GooglClient implements UrlShortenerClient {
	private final RestTemplate restTemplate;
	private final EnqueteProps props;

	@java.beans.ConstructorProperties({"restTemplate", "props"})
	public GooglClient(RestTemplate restTemplate, EnqueteProps props) {
		this.restTemplate = restTemplate;
		this.props = props;
	}

	@Override
	@Cacheable
	public String shorten(String longUrl) {
		Map<String, String> body = Collections.singletonMap("longUrl", longUrl);
		try {
			JsonNode response = restTemplate.postForObject(
					"https://www.googleapis.com/urlshortener/v1/url?key={key}", body,
					JsonNode.class, props.getGoogl().getApiKey());
			if (response.has("id")) {
				return response.get("id").asText();
			}
			else {
				return null;
			}
		}
		catch (RestClientException e) {
			return null;
		}
	}
}
