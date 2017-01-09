package am.ik.openenquete.urlshortener;

public interface UrlShortenerClient {
	/**
	 * Shortens the given long url
	 *
	 * @param longUrl url to shorten
	 * @return shortened url. <code>null</code> if the long url is invalid.
	 */
	String shorten(String longUrl);
}
