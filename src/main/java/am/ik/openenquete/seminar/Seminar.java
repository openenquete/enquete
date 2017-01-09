package am.ik.openenquete.seminar;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import am.ik.openenquete.session.Session;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	@Getter(onMethod = @__(@JsonIgnore))
	private List<Session> sessions;
	@OneToOne(mappedBy = "seminar", cascade = CascadeType.ALL, orphanRemoval = true)
	@Setter
	private SeminarClosed seminarClosed;
	@Column(insertable = false, updatable = false)
	private Instant updatedAt;
	@Column(insertable = false, updatable = false)
	private Instant createdAt;

	@Transient
	public boolean isOpen() {
		return this.seminarClosed == null;
	}
}
