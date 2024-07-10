package org.depromeet.spot.application.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
                authorize -> authorize
                    // 테스트, 개발 중엔 모든 경로 오픈.
                    .requestMatchers("/**").permitAll()
            );
        return http.build();
    }
}
