package am.ik.openenquete.security;

import am.ik.openenquete.EnqueteUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class EnqueteUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

    private final EnqueteAuthoritiesExtractor enqueteAuthoritiesExtractor;

    public EnqueteUserService(EnqueteAuthoritiesExtractor enqueteAuthoritiesExtractor) {
        this.enqueteAuthoritiesExtractor = enqueteAuthoritiesExtractor;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = this.delegate.loadUser(userRequest);
        Map<String, Object> attributes = user.getAttributes();
        List<GrantedAuthority> authorities = this.enqueteAuthoritiesExtractor.extractAuthorities(attributes);
        EnqueteUser enqueteUser = new EnqueteUser(Objects.toString(attributes.get("name"), user.getName()),
            user.getName(),
            Objects.toString(attributes.get("email")),
            Objects.toString(attributes.get("avatar_url")),
            authorities);
        return enqueteUser;
    }
}
