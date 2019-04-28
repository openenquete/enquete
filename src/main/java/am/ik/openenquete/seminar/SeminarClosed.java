package am.ik.openenquete.seminar;

import am.ik.openenquete.questionnaire.UsernameHolder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
public class SeminarClosed implements Serializable, UsernameHolder {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")
	private UUID closedId;
	@OneToOne
	@JoinColumn(name = "seminar_id")
	private Seminar seminar;
	@Column(updatable = false)
	private String username;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;

	public SeminarClosed(UUID closedId, Seminar seminar, String username, Instant createdAt) {
		this.closedId = closedId;
		this.seminar = seminar;
		this.username = username;
		this.createdAt = createdAt;
	}

	public SeminarClosed() {
	}

	public static SeminarClosedBuilder builder() {
		return new SeminarClosedBuilder();
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof SeminarClosed)) {
			return false;
		}
		final SeminarClosed other = (SeminarClosed) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		final Object this$closedId = this.getClosedId();
		final Object other$closedId = other.getClosedId();
		if (this$closedId == null ? other$closedId != null : !this$closedId.equals(other$closedId)) {
			return false;
		}
		final Object this$username = this.getUsername();
		final Object other$username = other.getUsername();
		if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
			return false;
		}
		final Object this$createdAt = this.getCreatedAt();
		final Object other$createdAt = other.getCreatedAt();
		if (this$createdAt == null ? other$createdAt != null : !this$createdAt.equals(other$createdAt)) {
			return false;
		}
		return true;
	}

	public UUID getClosedId() {
		return this.closedId;
	}

	public Instant getCreatedAt() {
		return this.createdAt;
	}

	@JsonIgnore
	public Seminar getSeminar() {
		return this.seminar;
	}

	public String getUsername() {
		return this.username;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $closedId = this.getClosedId();
		result = result * PRIME + ($closedId == null ? 43 : $closedId.hashCode());
		final Object $username = this.getUsername();
		result = result * PRIME + ($username == null ? 43 : $username.hashCode());
		final Object $createdAt = this.getCreatedAt();
		result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
		return result;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return "SeminarClosed(closedId=" + this.getClosedId() + ", username=" + this.getUsername() + ", createdAt=" + this.getCreatedAt() + ")";
	}

	protected boolean canEqual(final Object other) {
		return other instanceof SeminarClosed;
	}

	public static class SeminarClosedBuilder {

		private UUID closedId;

		private Instant createdAt;

		private Seminar seminar;

		private String username;

		SeminarClosedBuilder() {
		}

		public SeminarClosed build() {
			return new SeminarClosed(closedId, seminar, username, createdAt);
		}

		public SeminarClosed.SeminarClosedBuilder closedId(UUID closedId) {
			this.closedId = closedId;
			return this;
		}

		public SeminarClosed.SeminarClosedBuilder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public SeminarClosed.SeminarClosedBuilder seminar(Seminar seminar) {
			this.seminar = seminar;
			return this;
		}

		public String toString() {
			return "SeminarClosed.SeminarClosedBuilder(closedId=" + this.closedId + ", seminar=" + this.seminar + ", username=" + this.username + ", createdAt=" + this.createdAt + ")";
		}

		public SeminarClosed.SeminarClosedBuilder username(String username) {
			this.username = username;
			return this;
		}
	}
}
