package am.ik.openenquete.session;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import am.ik.openenquete.seminar.Seminar;
import lombok.*;

@Getter
@ToString(exclude = "seminar")
@EqualsAndHashCode(exclude = "seminar")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	@Setter
	private Seminar seminar;
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;
}
