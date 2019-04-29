package am.ik.openenquete.security;

import am.ik.openenquete.EnqueteProps;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final EnqueteUserService enqueteUserService;

    private final EnqueteProps props;

    public SecurityConfig(EnqueteUserService enqueteUserService, EnqueteProps props) {
        this.enqueteUserService = enqueteUserService;
        this.props = props;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/css/**", "/js/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers() //
            .mvcMatchers("v1/seminars/{seminarId}/votes") //
            .and() //
            .authorizeRequests() //
            /* */.mvcMatchers("v1/seminars/{seminarId}/votes").authenticated() //
            .and() //
            .httpBasic() //
            .and() //
            .requestMatchers()
            .antMatchers("/**") //
            .and() //
            .authorizeRequests() //
            /* */.mvcMatchers("v1/responses_for_session", "v1/responses_for_seminar", "v1/coupons/**", "v1/coupon_used", "seminars/*", "sessions/*", "coupons/*", "actuator/health",
            "actuator/info",
            "actuator/prometheus").permitAll() //
            /* */.mvcMatchers("/admin", "/admin/**", "/v1/**", "/actuator/**").hasRole("ADMIN") //
            /* */.antMatchers("/login**").permitAll() //
            /* */.anyRequest().authenticated() //
            .and() //
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .and() //
            .logout() //
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
            .and()
            .oauth2Login().userInfoEndpoint().userService(this.enqueteUserService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser(this.props.getAdminClient().asUser());
    }
}
