package am.ik.openenquete;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EnqueteUser implements OAuth2User {

    private final String name;

    private final String github;

    private final String email;

    private final String avatarUrl;

    private final List<GrantedAuthority> authorities;

    public EnqueteUser(String name, String github, String email, String avatarUrl, List<GrantedAuthority> authorities) {
        this.name = Objects.toString(name, github);
        this.github = github;
        this.email = email;
        this.avatarUrl = avatarUrl;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Collections.emptyMap();
    }

    public String getAvatarUrl() {
        return this.avatarUrl;
    }

    public String getEmail() {
        return this.email;
    }

    public String getGithub() {
        return this.github;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "EnqueteUser{" +
            "name='" + name + '\'' +
            ", github='" + github + '\'' +
            ", email='" + email + '\'' +
            ", avatarUrl='" + avatarUrl + '\'' +
            '}';
    }
}
