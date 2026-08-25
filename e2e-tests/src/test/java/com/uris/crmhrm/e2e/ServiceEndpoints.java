package com.uris.crmhrm.e2e;

import java.util.LinkedHashMap;
import java.util.Map;

final class ServiceEndpoints {

    private ServiceEndpoints() {
    }

    static String gateway() {
        return envOrDefault("E2E_GATEWAY_URL", "http://localhost:8080");
    }

    static Map<String, String> all() {
        Map<String, String> services = new LinkedHashMap<>();
        services.put("api-gateway", envOrDefault("E2E_API_GATEWAY_URL", "http://localhost:8080"));
        services.put("employee-service", envOrDefault("E2E_EMPLOYEE_SERVICE_URL", "http://localhost:8081"));
        services.put("client-service", envOrDefault("E2E_CLIENT_SERVICE_URL", "http://localhost:8082"));
        services.put("task-service", envOrDefault("E2E_TASK_SERVICE_URL", "http://localhost:8083"));
        services.put("notification-service", envOrDefault("E2E_NOTIFICATION_SERVICE_URL", "http://localhost:8084"));
        return services;
    }

    private static String envOrDefault(String name, String fallback) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? fallback : value;
    }
}
