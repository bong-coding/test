package com.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 *
 * 목적:
 * - /api/health 는 배포 확인용이므로 인증 없이 허용
 * - 나머지 요청은 기존처럼 인증 필요
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // REST API 테스트 시 CSRF 때문에 막히는 것을 방지
                // 개발/테스트용 설정이다.
                .csrf(csrf -> csrf.disable())

                // URL별 접근 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 배포 확인용 API는 로그인 없이 접근 허용
                        .requestMatchers("/api/health").permitAll()

                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )

                // 기존 Spring Security 기본 Basic Auth 유지
                .httpBasic(Customizer.withDefaults())

                .build();
    }
}