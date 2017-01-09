package am.ik.openenquete.urlshortener;

import java.net.URI;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;

import am.ik.openenquete.JjugProps;
import lombok.RequiredArgsConstructor;

@ConditionalOnProperty(name = "jjug.bitly.access-token")
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "urlshortener")
public class BitlyClient implements UrlShortenerClient {
	private final RestTemplate restTemplate;
	private final JjugProps props;

	@Override
	@Cacheable
	public String shorten(String longUrl) {
		final URI url = UriComponentsBuilder.fromHttpUrl("https://api-ssl.bitly.com")
				.pathSegment("v3", "shorten")
				.queryParam("access_token", props.getBitly().getAccessToken())
				.queryParam("longUrl", longUrl).build().encode().toUri();
		try {
			JsonNode response = restTemplate.getForObject(url, JsonNode.class);
			if (response.has("data") && response.get("data").has("url")) {
				return response.get("data").get("url").asText();
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
