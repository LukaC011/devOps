package com.uris.crmhrm.task_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class EmployeeClient {

    private final RestClient restClient;

    public EmployeeClient(RestClient.Builder restClientBuilder,
            @Value("${employee-service.url}") String employeeServiceUrl) {
        this.restClient = restClientBuilder.baseUrl(employeeServiceUrl).build();
    }

    public boolean employeeExists(Long employeeId) {
        return restClient.get()
                .uri("/employees/{id}", employeeId)
                .exchange((request, response) -> response.getStatusCode().is2xxSuccessful());
    }
}
