package am.ik.openenquete.session;

import am.ik.openenquete.questionnaire.IpAddressHolder;
import am.ik.openenquete.questionnaire.UsernameHolder;
import am.ik.openenquete.questionnaire.enums.Difficulty;
import am.ik.openenquete.questionnaire.enums.Satisfaction;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
public class ResponseForSession implements Serializable, UsernameHolder, IpAddressHolder {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")
	private UUID responseForSessionId;
	@NotNull
	@Column(columnDefinition = "tinyint")
	private Satisfaction satisfaction;
	@NotNull
	@Column(columnDefinition = "tinyint")
	private Difficulty difficulty;
	private String comment;
	@ManyToOne
	@JoinColumn(name = "session_id", updatable = false)
	@NotNull
	private Session session;
	@Column(updatable = false)
	private String username;
	@Column(updatable = false)
	private String ipAddress;
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;

	public ResponseForSession(UUID responseForSessionId, Satisfaction satisfaction, Difficulty difficulty, String comment, Session session, String username, String ipAddress, Instant updatedAt,
							  Instant createdAt) {
		this.responseForSessionId = responseForSessionId;
		this.satisfaction = satisfaction;
		this.difficulty = difficulty;
		this.comment = comment;
		this.session = session;
		this.username = username;
		this.ipAddress = ipAddress;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public ResponseForSession() {
	}

	public static ResponseForSessionBuilder builder() {
		return new ResponseForSessionBuilder();
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ResponseForSession)) {
			return false;
		}
		final ResponseForSession other = (ResponseForSession) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		final Object this$responseForSessionId = this.getResponseForSessionId();
		final Object other$responseForSessionId = other.getResponseForSessionId();
		if (this$responseForSessionId == null ? other$responseForSessionId != null : !this$responseForSessionId.equals(other$responseForSessionId)) {
			return false;
		}
		final Object this$satisfaction = this.getSatisfaction();
		final Object other$satisfaction = other.getSatisfaction();
		if (this$satisfaction == null ? other$satisfaction != null : !this$satisfaction.equals(other$satisfaction)) {
			return false;
		}
		final Object this$difficulty = this.getDifficulty();
		final Object other$difficulty = other.getDifficulty();
		if (this$difficulty == null ? other$difficulty != null : !this$difficulty.equals(other$difficulty)) {
			return false;
		}
		final Object this$comment = this.getComment();
		final Object other$comment = other.getComment();
		if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) {
			return false;
		}
		final Object this$session = this.getSession();
		final Object other$session = other.getSession();
		if (this$session == null ? other$session != null : !this$session.equals(other$session)) {
			return false;
		}
		final Object this$username = this.getUsername();
		final Object other$username = other.getUsername();
		if (this$username == null ? other$username != null : !this$username.equals(other$username)) {
			return false;
		}
		final Object this$ipAddress = this.getIpAddress();
		final Object other$ipAddress = other.getIpAddress();
		if (this$ipAddress == null ? other$ipAddress != null : !this$ipAddress.equals(other$ipAddress)) {
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

	public String getComment() {
		return this.comment;
	}

	public Instant getCreatedAt() {
		return this.createdAt;
	}

	public Difficulty getDifficulty() {
		return this.difficulty;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public UUID getResponseForSessionId() {
		return this.responseForSessionId;
	}

	public Satisfaction getSatisfaction() {
		return this.satisfaction;
	}

	public Session getSession() {
		return this.session;
	}

	public Instant getUpdatedAt() {
		return this.updatedAt;
	}

	public String getUsername() {
		return this.username;
	}

	public int hashCode() {
		final int PRIME = 59;
		int result = 1;
		final Object $responseForSessionId = this.getResponseForSessionId();
		result = result * PRIME + ($responseForSessionId == null ? 43 : $responseForSessionId.hashCode());
		final Object $satisfaction = this.getSatisfaction();
		result = result * PRIME + ($satisfaction == null ? 43 : $satisfaction.hashCode());
		final Object $difficulty = this.getDifficulty();
		result = result * PRIME + ($difficulty == null ? 43 : $difficulty.hashCode());
		final Object $comment = this.getComment();
		result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
		final Object $session = this.getSession();
		result = result * PRIME + ($session == null ? 43 : $session.hashCode());
		final Object $username = this.getUsername();
		result = result * PRIME + ($username == null ? 43 : $username.hashCode());
		final Object $ipAddress = this.getIpAddress();
		result = result * PRIME + ($ipAddress == null ? 43 : $ipAddress.hashCode());
		final Object $updatedAt = this.getUpdatedAt();
		result = result * PRIME + ($updatedAt == null ? 43 : $updatedAt.hashCode());
		final Object $createdAt = this.getCreatedAt();
		result = result * PRIME + ($createdAt == null ? 43 : $createdAt.hashCode());
		return result;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String toString() {
		return "ResponseForSession(responseForSessionId=" + this.getResponseForSessionId() + ", satisfaction=" + this.getSatisfaction() + ", difficulty=" + this.getDifficulty() + ", comment=" + this.getComment() + ", session=" + this.getSession() + ", username=" + this.getUsername() + ", ipAddress=" + this.getIpAddress() + ", updatedAt=" + this.getUpdatedAt() + ", createdAt=" + this.getCreatedAt() + ")";
	}

	protected boolean canEqual(final Object other) {
		return other instanceof ResponseForSession;
	}

	public static class ResponseForSessionBuilder {

		private String comment;

		private Instant createdAt;

		private Difficulty difficulty;

		private String ipAddress;

		private UUID responseForSessionId;

		private Satisfaction satisfaction;

		private Session session;

		private Instant updatedAt;

		private String username;

		ResponseForSessionBuilder() {
		}

		public ResponseForSession build() {
			return new ResponseForSession(responseForSessionId, satisfaction, difficulty, comment, session, username, ipAddress, updatedAt, createdAt);
		}

		public ResponseForSession.ResponseForSessionBuilder comment(String comment) {
			this.comment = comment;
			return this;
		}

		public ResponseForSession.ResponseForSessionBuilder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public ResponseForSession.ResponseForSessionBuilder difficulty(Difficulty difficulty) {
			this.difficulty = difficulty;
			return this;
		}

		public ResponseForSession.ResponseForSessionBuilder ipAddress(String ipAddress) {
			this.ipAddress = ipAddress;
			return this;
		}

		public ResponseForSession.ResponseForSessionBuilder responseForSessionId(UUID responseForSessionId) {
			this.responseForSessionId = responseForSessionId;
			return this;
		}

		public ResponseForSession.ResponseForSessionBuilder satisfaction(Satisfaction satisfaction) {
			this.satisfaction = satisfaction;
			return this;
		}

		public ResponseForSession.ResponseForSessionBuilder session(Session session) {
			this.session = session;
			return this;
		}

		public String toString() {
			return "ResponseForSession.ResponseForSessionBuilder(responseForSessionId=" + this.responseForSessionId + ", satisfaction=" + this.satisfaction + ", difficulty=" + this.difficulty + ", " +
				"comment=" + this.comment + ", session=" + this.session + ", username=" + this.username + ", ipAddress=" + this.ipAddress + ", updatedAt=" + this.updatedAt + ", createdAt=" + this.createdAt + ")";
		}

		public ResponseForSession.ResponseForSessionBuilder updatedAt(Instant updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public ResponseForSession.ResponseForSessionBuilder username(String username) {
			this.username = username;
			return this;
		}
	}
}
