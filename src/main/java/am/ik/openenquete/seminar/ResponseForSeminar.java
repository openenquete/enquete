package am.ik.openenquete.seminar;

import am.ik.openenquete.questionnaire.IpAddressHolder;
import am.ik.openenquete.questionnaire.UsernameHolder;
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
public class ResponseForSeminar implements Serializable, UsernameHolder, IpAddressHolder {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")
	private UUID responseForSeminarId;
	@NotNull
	@Column(columnDefinition = "tinyint")
	private Satisfaction satisfaction;
	private String request;
	private String comment;
	@ManyToOne
	@JoinColumn(name = "seminar_id", updatable = false)
	@NotNull
	private Seminar seminar;
	@Column(updatable = false)
	private String username;
	@Column(updatable = false)
	private String ipAddress;
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;

	public ResponseForSeminar(UUID responseForSeminarId, Satisfaction satisfaction, String request, String comment, Seminar seminar, String username, String ipAddress, Instant updatedAt,
							  Instant createdAt) {
		this.responseForSeminarId = responseForSeminarId;
		this.satisfaction = satisfaction;
		this.request = request;
		this.comment = comment;
		this.seminar = seminar;
		this.username = username;
		this.ipAddress = ipAddress;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public ResponseForSeminar() {
	}

	public static ResponseForSeminarBuilder builder() {
		return new ResponseForSeminarBuilder();
	}

	public boolean equals(final Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof ResponseForSeminar)) {
			return false;
		}
		final ResponseForSeminar other = (ResponseForSeminar) o;
		if (!other.canEqual((Object) this)) {
			return false;
		}
		final Object this$responseForSeminarId = this.getResponseForSeminarId();
		final Object other$responseForSeminarId = other.getResponseForSeminarId();
		if (this$responseForSeminarId == null ? other$responseForSeminarId != null : !this$responseForSeminarId.equals(other$responseForSeminarId)) {
			return false;
		}
		final Object this$satisfaction = this.getSatisfaction();
		final Object other$satisfaction = other.getSatisfaction();
		if (this$satisfaction == null ? other$satisfaction != null : !this$satisfaction.equals(other$satisfaction)) {
			return false;
		}
		final Object this$request = this.getRequest();
		final Object other$request = other.getRequest();
		if (this$request == null ? other$request != null : !this$request.equals(other$request)) {
			return false;
		}
		final Object this$comment = this.getComment();
		final Object other$comment = other.getComment();
		if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) {
			return false;
		}
		final Object this$seminar = this.getSeminar();
		final Object other$seminar = other.getSeminar();
		if (this$seminar == null ? other$seminar != null : !this$seminar.equals(other$seminar)) {
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

	public String getIpAddress() {
		return this.ipAddress;
	}

	public String getRequest() {
		return this.request;
	}

	public UUID getResponseForSeminarId() {
		return this.responseForSeminarId;
	}

	public Satisfaction getSatisfaction() {
		return this.satisfaction;
	}

	public Seminar getSeminar() {
		return this.seminar;
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
		final Object $responseForSeminarId = this.getResponseForSeminarId();
		result = result * PRIME + ($responseForSeminarId == null ? 43 : $responseForSeminarId.hashCode());
		final Object $satisfaction = this.getSatisfaction();
		result = result * PRIME + ($satisfaction == null ? 43 : $satisfaction.hashCode());
		final Object $request = this.getRequest();
		result = result * PRIME + ($request == null ? 43 : $request.hashCode());
		final Object $comment = this.getComment();
		result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
		final Object $seminar = this.getSeminar();
		result = result * PRIME + ($seminar == null ? 43 : $seminar.hashCode());
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
		return "ResponseForSeminar(responseForSeminarId=" + this.getResponseForSeminarId() + ", satisfaction=" + this.getSatisfaction() + ", request=" + this.getRequest() + ", comment=" + this.getComment() + ", seminar=" + this.getSeminar() + ", username=" + this.getUsername() + ", ipAddress=" + this.getIpAddress() + ", updatedAt=" + this.getUpdatedAt() + ", createdAt=" + this.getCreatedAt() + ")";
	}

	protected boolean canEqual(final Object other) {
		return other instanceof ResponseForSeminar;
	}

	public static class ResponseForSeminarBuilder {

		private String comment;

		private Instant createdAt;

		private String ipAddress;

		private String request;

		private UUID responseForSeminarId;

		private Satisfaction satisfaction;

		private Seminar seminar;

		private Instant updatedAt;

		private String username;

		ResponseForSeminarBuilder() {
		}

		public ResponseForSeminar build() {
			return new ResponseForSeminar(responseForSeminarId, satisfaction, request, comment, seminar, username, ipAddress, updatedAt, createdAt);
		}

		public ResponseForSeminar.ResponseForSeminarBuilder comment(String comment) {
			this.comment = comment;
			return this;
		}

		public ResponseForSeminar.ResponseForSeminarBuilder createdAt(Instant createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public ResponseForSeminar.ResponseForSeminarBuilder ipAddress(String ipAddress) {
			this.ipAddress = ipAddress;
			return this;
		}

		public ResponseForSeminar.ResponseForSeminarBuilder request(String request) {
			this.request = request;
			return this;
		}

		public ResponseForSeminar.ResponseForSeminarBuilder responseForSeminarId(UUID responseForSeminarId) {
			this.responseForSeminarId = responseForSeminarId;
			return this;
		}

		public ResponseForSeminar.ResponseForSeminarBuilder satisfaction(Satisfaction satisfaction) {
			this.satisfaction = satisfaction;
			return this;
		}

		public ResponseForSeminar.ResponseForSeminarBuilder seminar(Seminar seminar) {
			this.seminar = seminar;
			return this;
		}

		public String toString() {
			return "ResponseForSeminar.ResponseForSeminarBuilder(responseForSeminarId=" + this.responseForSeminarId + ", satisfaction=" + this.satisfaction + ", request=" + this.request + ", comment" +
				"=" + this.comment + ", seminar=" + this.seminar + ", username=" + this.username + ", ipAddress=" + this.ipAddress + ", updatedAt=" + this.updatedAt + ", createdAt=" + this.createdAt + ")";
		}

		public ResponseForSeminar.ResponseForSeminarBuilder updatedAt(Instant updatedAt) {
			this.updatedAt = updatedAt;
			return this;
		}

		public ResponseForSeminar.ResponseForSeminarBuilder username(String username) {
			this.username = username;
			return this;
		}
	}
}
