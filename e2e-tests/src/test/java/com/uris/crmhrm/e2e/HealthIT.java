package com.uris.crmhrm.e2e;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HealthIT {

    static Stream<Arguments> services() {
        return ServiceEndpoints.all().entrySet().stream()
                .map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest(name = "{0} vraca UP")
    @MethodSource("services")
    void reportsUp(String serviceName, String baseUrl) {
        given()
                .baseUri(baseUrl)
        .when()
                .get("/actuator/health")
        .then()
                .statusCode(200)
                .body("status", equalTo("UP"));
    }
}
