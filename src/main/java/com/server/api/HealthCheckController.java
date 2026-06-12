package com.server.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 배포 상태 확인용 API
 *
 * 목적:
 * - Docker 컨테이너가 정상 실행 중인지 확인
 * - Nginx가 Spring Boot까지 요청을 전달하는지 확인
 * - GitHub Actions 자동 배포 후 간단한 테스트에 사용
 */
@RestController
public class HealthCheckController {

    private final Environment environment;

    @Value("${spring.application.name:server}")
    private String applicationName;

    public HealthCheckController(Environment environment) {
        this.environment = environment;
    }

    /**
     * 서버 상태 확인 API
     *
     * 요청:
     * GET /api/health
     */
    @GetMapping("/api/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new LinkedHashMap<>();

        response.put("status", "ok");
        response.put("app", applicationName);
        response.put("profiles", environment.getActiveProfiles());

        return response;
    }
}