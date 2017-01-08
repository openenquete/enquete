package jjug.session;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import jjug.questionnaire.IpAddressHolder;
import jjug.questionnaire.UsernameHolder;
import jjug.questionnaire.enums.Difficulty;
import jjug.questionnaire.enums.Satisfaction;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	@Setter
	@Column(updatable = false)
	private String username;
	@Setter
	@Column(updatable = false)
	private String ipAddress;
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;
}
