package dudu.webservice.config.auth;

import dudu.webservice.config.auth.CustomOAuth2UserService;
import dudu.webservice.web.domain.user.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // CSRF 비활성화 (개발 환경에서만 사용)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Frame Options 비활성화 (람다 표현식)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/") // 로그아웃 성공 시 이동할 URL
                )
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService) // CustomOAuth2UserService 등록
                        )
                        .defaultSuccessUrl("/home", true) // 로그인 성공 시 리다이렉트할 경로
                );

        return http.build();
    }
}