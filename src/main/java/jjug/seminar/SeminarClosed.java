package jjug.seminar;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jjug.questionnaire.UsernameHolder;
import lombok.*;

@Getter
@ToString(exclude = "seminar")
@EqualsAndHashCode(exclude = "seminar")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class SeminarClosed implements Serializable, UsernameHolder {
	@Id
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@GeneratedValue(generator = "uuid")
	@Column(columnDefinition = "binary(16)")
	private UUID closedId;
	@OneToOne
	@JoinColumn(name = "seminar_id")
	@Getter(onMethod = @__(@JsonIgnore))
	private Seminar seminar;
	@Setter
	@Column(updatable = false)
	private String username;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;
}
