package warehouse.common.config.security;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import warehouse.domain.users.security.jwt.filter.JwtAuthFilter;
import warehouse.domain.users.security.jwt.ifs.TokenHelperIfs;
import warehouse.domain.users.security.jwt.service.TokenService;
import warehouse.domain.users.security.service.AuthorizationService;

@Configuration
@EnableWebSecurity // security 활성화
@EnableGlobalAuthentication
@AllArgsConstructor
public class SecurityConfig{

    private final AuthorizationService authorizationService;
    private final TokenService tokenService;

    private final List<String> WHITE_LIST = List.of(
        "/swagger-ui.html",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/open-api/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            .addFilterBefore(new JwtAuthFilter(authorizationService, tokenService), UsernamePasswordAuthenticationFilter.class)
            .csrf((csrfConfig) ->
                csrfConfig.disable()
            ) // 1번
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(it -> {
                it
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers(WHITE_LIST.toArray(new String[0])).permitAll()
                    .anyRequest().authenticated();
            })
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

        ;

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(WHITE_LIST.toArray(new String[0]));
    }

}
