package jjug.session;

import java.util.List;
import java.util.UUID;

import lombok.Value;

public interface Summary<T extends Comparable<T>> {

	T getValue();

	Long getTotal();

	UUID getSessionId();

	String getSessionName();

	UUID getSeminarId();

	Long getCount();

	default Session asSession() {
		return new Session(getSessionId(), getSessionName());
	}

	default Detail<T> asDetail() {
		return new Detail<>(getValue(), getTotal(), getCount());
	}

	@Value
	final class Session {
		private final UUID sessionId;
		private final String sessionName;
	}

	@Value
	class Report<T extends Comparable<T>> {
		private final double average;
		private final long total;
		private final long count;
		private final List<Detail<T>> details;
	}

	@Value
	class Detail<T extends Comparable<T>> {
		private final T value;
		private final long total;
		private final long count;
	}
}
