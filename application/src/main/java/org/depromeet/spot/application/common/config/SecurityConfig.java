package org.depromeet.spot.application.common.config;

import org.depromeet.spot.application.common.jwt.JwtAuthenticationFilter;
import org.depromeet.spot.application.common.jwt.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;

    private static final String[] AUTH_WHITELIST = {
        "/api/**",
        "/swagger-ui/**",
        "/api-docs",
        "swagger-ui-custom.html",
        "/v3/api-docs/**",
        "/api-docs/**",
        "/swagger-ui.html",
        "/favicon.ico/**",
        "/api/v1/members/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // cross-site -> stateless라서 필요 없음.
                .csrf(AbstractHttpConfigurer::disable)
                // 초기 로그인 화면 필요 없음.
                .formLogin(AbstractHttpConfigurer::disable)
                // 토큰 방식을 사용하므로 httpBasic도 제거.
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize ->
                                authorize
                                        .requestMatchers(AUTH_WHITELIST)
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated())
                // UsernamePasswordAuthenticationFilter 필터 전에 jwt 필터가 먼저 동작하도록함.
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenUtil),
                        UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
