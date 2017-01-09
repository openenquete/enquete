package am.ik.openenquete;

import java.io.Serializable;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Builder
public class JjugUser implements Serializable {
	private final String name;
	private final String github;
	private final String email;
	private final String avatarUrl;
}
