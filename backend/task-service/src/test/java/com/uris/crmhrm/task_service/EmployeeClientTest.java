package com.uris.crmhrm.task_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

class EmployeeClientTest {

    private static final String BASE_URL = "http://employee-service:8081";

    private MockRestServiceServer server;
    private EmployeeClient employeeClient;

    @BeforeEach
    void setUp() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        employeeClient = new EmployeeClient(builder, BASE_URL);
    }

    @Test
    void reportsExistingEmployee() {
        server.expect(requestTo(BASE_URL + "/employees/1"))
                .andRespond(withSuccess("{\"id\":1}", MediaType.APPLICATION_JSON));

        assertThat(employeeClient.employeeExists(1L)).isTrue();
        server.verify();
    }

    @Test
    void reportsMissingEmployee() {
        server.expect(requestTo(BASE_URL + "/employees/99"))
                .andRespond(withResourceNotFound());

        assertThat(employeeClient.employeeExists(99L)).isFalse();
        server.verify();
    }
}
