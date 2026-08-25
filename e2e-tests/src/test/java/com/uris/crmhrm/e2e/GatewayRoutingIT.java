package com.uris.crmhrm.e2e;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GatewayRoutingIT {

    @ParameterizedTest(name = "gateway rutira {0}")
    @ValueSource(strings = {"/api/employees", "/api/clients", "/api/tasks", "/api/notifications"})
    void routesToBackingService(String path) {
        given()
                .baseUri(ServiceEndpoints.gateway())
        .when()
                .get(path)
        .then()
                .statusCode(200);
    }

    @ParameterizedTest(name = "gateway prosledjuje stream {0}")
    @ValueSource(strings = {"/api/clients/stream", "/api/clients/flowable"})
    void routesReactiveStreams(String path) {
        given()
                .baseUri(ServiceEndpoints.gateway())
                .accept("text/event-stream")
        .when()
                .get(path)
        .then()
                .statusCode(200)
                .contentType("text/event-stream;charset=UTF-8");
    }
}
