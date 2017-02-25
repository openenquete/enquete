package am.ik.openenquete.urlshortener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import am.ik.openenquete.EnqueteProps;
import lombok.RequiredArgsConstructor;

@ConditionalOnProperty(name = "enquete.bitly.access-token")
@Component
@RequiredArgsConstructor
@CacheConfig(cacheNames = "urlshortener")
public class BitlyClient implements UrlShortenerClient {
	private final RestTemplate restTemplate;
	private final EnqueteProps props;

	@Override
	@Cacheable
	public String shorten(String longUrl) {
		try {
			JsonNode response = restTemplate.getForObject(
					"https://api-ssl.bitly.com/v3/shorten?access_token={token}&longUrl={url}",
					JsonNode.class, props.getBitly().getAccessToken(), longUrl);
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
