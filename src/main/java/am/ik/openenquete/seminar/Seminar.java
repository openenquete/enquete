package am.ik.openenquete.seminar;

import am.ik.openenquete.session.Session;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
public class Seminar implements Serializable {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")
	private UUID seminarId;
	private String seminarName;
	private LocalDate seminarDate;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "seminar")
	@OrderBy("sessionName ASC")
	private List<Session> sessions;
	@OneToOne(mappedBy = "seminar", cascade = CascadeType.ALL, orphanRemoval = true)
	private SeminarClosed seminarClosed;
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;

	public Seminar(UUID seminarId, String seminarName, LocalDate seminarDate, List<Session> sessions, SeminarClosed seminarClosed, Instant updatedAt, Instant createdAt) {
		this.seminarId = seminarId;
		this.seminarName = seminarName;
		this.seminarDate = seminarDate;
		this.sessions = sessions;
		this.seminarClosed = seminarClosed;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public Seminar() {
	}

	public static SeminarBuilder builder() {
		return new SeminarBuilder();
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Seminar)) {
			return false;
		}
		final Seminar other = (Seminar) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		final Object this$seminarId = this.getSeminarId();
		final Object other$seminarId = other.getSeminarId();
		if (this$seminarId == null ? other$seminarId != null : !this$seminarId.equals(other$seminarId)) {
			return false;
		}
		final Object this$seminarName = this.getSeminarName();
		final Object other$seminarName = other.getSeminarName();
		if (this$seminarName == null ? other$seminarName != null : !this$seminarName.equals(other$seminarName)) {
			return false;
		}
		final Object this$seminarDate = this.getSeminarDate();
		final Object other$seminarDate = other.getSeminarDate();
		if (this$seminarDate == null ? other$seminarDate != null : !this$seminarDate.equals(other$seminarDate)) {
			return false;
		}
		final Object this$sessions = this.getSessions();
		final Object other$sessions = other.getSessions();
		if (this$sessions == null ? other$sessions != null : !this$sessions.equals(other$sessions)) {
			return false;
		}
		final Object this$seminarClosed = this.getSeminarClosed();
		final Object other$seminarClosed = other.getSeminarClosed();
		if (this$seminarClosed == null ? other$seminarClosed != null : !this$seminarClosed.equals(other$seminarClosed)) {
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

	public SeminarClosed getSeminarClosed() {
		return this.seminarClosed;
	}

	public LocalDate getSeminarDate() {
		return this.seminarDate;
	}

	public UUID getSeminarId() {
		return this.seminarId;
	}

	public String getSeminarName() {
		return this.seminarName;
	}

	@JsonIgnore
	public List<Session> getSessions() {
		return this.sessions;
	}

	public Instant getUpdatedAt() {
		return this.updatedAt;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $seminarId = this.getSeminarId();
		result = result * PRIME + ($seminarId == null ? 43 : $seminarId.hashCode());
		final Object $seminarName = this.getSeminarName();
		result = result * PRIME + ($seminarName == null ? 43 : $seminarName.hashCode());
		final Object $seminarDate = this.getSeminarDate();
		result = result * PRIME + ($seminarDate == null ? 43 : $seminarDate.hashCode());
		final Object $sessions = this.getSessions();
		result = result * PRIME + ($sessions == null ? 43 : $sessions.hashCode());
		final Object $seminarClosed = this.getSeminarClosed();
		result = result * PRIME + ($seminarClosed == null ? 43 : $seminarClosed.hashCode());
		final Object $updatedAt = this.getUpdatedAt();
		result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
		final Object $createdAt = this.getCreatedAt();
		result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
		return result;
	}

	@Transient
	public boolean isOpen() {
		return this.seminarClosed == null;
	}

	public void setSeminarClosed(SeminarClosed seminarClosed) {
		this.seminarClosed = seminarClosed;
	}

	public String toString() {
		return "Seminar(seminarId=" + this.getSeminarId() + ", seminarName=" + this.getSeminarName() + ", seminarDate=" + this.getSeminarDate() + ", sessions=" + this.getSessions() + ", " +
			"seminarClosed=" + this.getSeminarClosed() + ", updatedAt=" + this.getUpdatedAt() + ", createdAt=" + this.getCreatedAt() + ")";
	}

	protected boolean canEqual(final Object other) {
		return other instanceof Seminar;
	}

	public static class SeminarBuilder {

		private Instant createdAt;

		private SeminarClosed seminarClosed;

		private LocalDate seminarDate;

		private UUID seminarId;

		private String seminarName;

		private List<Session> sessions;

		private Instant updatedAt;

		SeminarBuilder() {
		}

		public Seminar build() {
			return new Seminar(seminarId, seminarName, seminarDate, sessions, seminarClosed, updatedAt, createdAt);
		}

		public Seminar.SeminarBuilder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Seminar.SeminarBuilder seminarClosed(SeminarClosed seminarClosed) {
			this.seminarClosed = seminarClosed;
			return this;
		}

		public Seminar.SeminarBuilder seminarDate(LocalDate seminarDate) {
			this.seminarDate = seminarDate;
			return this;
		}

		public Seminar.SeminarBuilder seminarId(UUID seminarId) {
			this.seminarId = seminarId;
			return this;
		}

		public Seminar.SeminarBuilder seminarName(String seminarName) {
			this.seminarName = seminarName;
			return this;
		}

		public Seminar.SeminarBuilder sessions(List<Session> sessions) {
			this.sessions = sessions;
			return this;
		}

		public String toString() {
			return "Seminar.SeminarBuilder(seminarId=" + this.seminarId + ", seminarName=" + this.seminarName + ", seminarDate=" + this.seminarDate + ", sessions=" + this.sessions + ", seminarClosed" +
				"=" + this.seminarClosed + ", updatedAt=" + this.updatedAt + ", createdAt=" + this.createdAt + ")";
		}

		public Seminar.SeminarBuilder updatedAt(Instant updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}
	}
}
