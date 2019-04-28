package am.ik.openenquete.session;

import am.ik.openenquete.seminar.Seminar;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
public class Session implements Serializable {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")
	private UUID sessionId;
	private String sessionName;
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "session_id"))
	@Column(name = "speaker")
	@OrderBy("speaker ASC")
	private List<String> speakers;
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name = "session_id"))
	@Column(name = "speaker_display_name")
	@OrderBy("speaker_display_name ASC")
	private List<String> speakerDisplayNames;
	@ManyToOne
	@JoinColumn(name = "seminar_id")
	private Seminar seminar;
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;

	public Session(UUID sessionId, String sessionName, List<String> speakers, List<String> speakerDisplayNames, Seminar seminar, Instant updatedAt, Instant createdAt) {
		this.sessionId = sessionId;
		this.sessionName = sessionName;
		this.speakers = speakers;
		this.speakerDisplayNames = speakerDisplayNames;
		this.seminar = seminar;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public Session() {
	}

	public static SessionBuilder builder() {
		return new SessionBuilder();
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Session)) {
			return false;
		}
		final Session other = (Session) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		final Object this$sessionId = this.getSessionId();
		final Object other$sessionId = other.getSessionId();
		if (this$sessionId == null ? other$sessionId != null : !this$sessionId.equals(other$sessionId)) {
			return false;
		}
		final Object this$sessionName = this.getSessionName();
		final Object other$sessionName = other.getSessionName();
		if (this$sessionName == null ? other$sessionName != null : !this$sessionName.equals(other$sessionName)) {
			return false;
		}
		final Object this$speakers = this.getSpeakers();
		final Object other$speakers = other.getSpeakers();
		if (this$speakers == null ? other$speakers != null : !this$speakers.equals(other$speakers)) {
			return false;
		}
		final Object this$speakerDisplayNames = this.getSpeakerDisplayNames();
		final Object other$speakerDisplayNames = other.getSpeakerDisplayNames();
		if (this$speakerDisplayNames == null ? other$speakerDisplayNames != null : !this$speakerDisplayNames.equals(other$speakerDisplayNames)) {
			return false;
		}
		final Object this$updatedAt = this.getUpdatedAt();
		final Object other$updatedAt = other.getUpdatedAt();
		if (this$updatedAt == null ? other$updatedAt != null : !this$updatedAt.equals(other$updatedAt)) {
			return false;
		}
		final Object this$createdAt = this.getCreatedAt();
		final Object other$createdAt = other.getCreatedAt();
		if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) {
			return false;
		}
		return true;
	}

	public Instant getCreatedAt() {
		return this.createdAt;
	}

	public Seminar getSeminar() {
		return this.seminar;
	}

	public UUID getSessionId() {
		return this.sessionId;
	}

	public String getSessionName() {
		return this.sessionName;
	}

	public List<String> getSpeakerDisplayNames() {
		return this.speakerDisplayNames;
	}

	public List<String> getSpeakers() {
		return this.speakers;
	}

	public Instant getUpdatedAt() {
		return this.updatedAt;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $sessionId = this.getSessionId();
		result = result * PRIME + ($sessionId == null ? 43 : $sessionId.hashCode());
		final Object $sessionName = this.getSessionName();
		result = result * PRIME + ($sessionName == null ? 43 : $sessionName.hashCode());
		final Object $speakers = this.getSpeakers();
		result = result * PRIME + ($speakers == null ? 43 : $speakers.hashCode());
		final Object $speakerDisplayNames = this.getSpeakerDisplayNames();
		result = result * PRIME + ($speakerDisplayNames == null ? 43 : $speakerDisplayNames.hashCode());
		final Object $updatedAt = this.getUpdatedAt();
		result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
		final Object $createdAt = this.getCreatedAt();
		result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
		return result;
	}

	public void setSeminar(Seminar seminar) {
		this.seminar = seminar;
	}

	public String toString() {
		return "Session(sessionId=" + this.getSessionId() + ", sessionName=" + this.getSessionName() + ", speakers=" + this.getSpeakers() + ", speakerDisplayNames=" + this.getSpeakerDisplayNames() + ", updatedAt=" + this.getUpdatedAt() + ", createdAt=" + this.getCreatedAt() + ")";
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Session;
	}

	public static class SessionBuilder {

		private Instant createdAt;

		private Seminar seminar;

		private UUID sessionId;

		private String sessionName;

		private List<String> speakerDisplayNames;

		private List<String> speakers;

		private Instant updatedAt;

		SessionBuilder() {
		}

		public Session build() {
			return new Session(sessionId, sessionName, speakers, speakerDisplayNames, seminar, updatedAt, createdAt);
		}

		public Session.SessionBuilder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Session.SessionBuilder seminar(Seminar seminar) {
			this.seminar = seminar;
			return this;
		}

		public Session.SessionBuilder sessionId(UUID sessionId) {
			this.sessionId = sessionId;
			return this;
		}

		public Session.SessionBuilder sessionName(String sessionName) {
			this.sessionName = sessionName;
			return this;
		}

		public Session.SessionBuilder speakerDisplayNames(List<String> speakerDisplayNames) {
			this.speakerDisplayNames = speakerDisplayNames;
			return this;
		}

		public Session.SessionBuilder speakers(List<String> speakers) {
			this.speakers = speakers;
			return this;
		}

		public String toString() {
			return "Session.SessionBuilder(sessionId=" + this.sessionId + ", sessionName=" + this.sessionName + ", speakers=" + this.speakers + ", speakerDisplayNames=" + this.speakerDisplayNames +
				", seminar=" + this.seminar + ", updatedAt=" + this.updatedAt + ", createdAt=" + this.createdAt + ")";
		}

		public Session.SessionBuilder updatedAt(Instant updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}
	}
}
