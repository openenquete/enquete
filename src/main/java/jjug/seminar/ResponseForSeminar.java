package jjug.seminar;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import jjug.questionnaire.IpAddressHolder;
import jjug.questionnaire.UsernameHolder;
import jjug.questionnaire.enums.Satisfaction;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ResponseForSeminar implements Serializable, UsernameHolder, IpAddressHolder {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")
	private UUID responseForSeminarId;
	@Enumerated(EnumType.STRING)
	@NotNull
	private Satisfaction satisfaction;
	private String request;
	private String comment;
	@ManyToOne
	@JoinColumn(name = "seminar_id", updatable = false)
	private Seminar seminar;
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
