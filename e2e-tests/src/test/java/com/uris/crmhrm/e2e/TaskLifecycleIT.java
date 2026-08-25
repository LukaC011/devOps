package com.uris.crmhrm.e2e;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.hasItem;

import io.restassured.http.ContentType;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TaskLifecycleIT {

    private static final Duration NOTIFICATION_TIMEOUT = Duration.ofSeconds(20);
    private static final Duration POLL_INTERVAL = Duration.ofMillis(500);
    private static final long UNKNOWN_EMPLOYEE_ID = 999999L;

    @Test
    void createdTaskEventuallyProducesNotification() {
        String title = "E2E zadatak " + UUID.randomUUID();
        int employeeId = createEmployee();

        int taskId = given()
                .baseUri(ServiceEndpoints.gateway())
                .contentType(ContentType.JSON)
                .body(taskPayload(title, employeeId))
        .when()
                .post("/api/tasks")
        .then()
                .statusCode(201)
                .extract().path("id");

        await().atMost(NOTIFICATION_TIMEOUT).pollInterval(POLL_INTERVAL).untilAsserted(() ->
                given()
                        .baseUri(ServiceEndpoints.gateway())
                .when()
                        .get("/api/notifications")
                .then()
                        .statusCode(200)
                        .body("findAll { it.taskId == " + taskId + " }.message", hasItem("Kreiran je zadatak: " + title)));
    }

    @Test
    void rejectsTaskForUnknownEmployee() {
        given()
                .baseUri(ServiceEndpoints.gateway())
                .contentType(ContentType.JSON)
                .body(taskPayload("Zadatak bez zaposlenog", UNKNOWN_EMPLOYEE_ID))
        .when()
                .post("/api/tasks")
        .then()
                .statusCode(404);
    }

    private int createEmployee() {
        String email = "e2e-" + UUID.randomUUID() + "@crm.rs";
        return given()
                .baseUri(ServiceEndpoints.gateway())
                .contentType(ContentType.JSON)
                .body("{\"firstName\":\"E2E\",\"lastName\":\"Provera\",\"position\":\"QA\",\"email\":\"" + email + "\"}")
        .when()
                .post("/api/employees")
        .then()
                .statusCode(201)
                .extract().path("id");
    }

    private String taskPayload(String title, long employeeId) {
        return "{\"title\":\"" + title + "\",\"description\":\"end to end provera\",\"employeeId\":" + employeeId + "}";
    }
}
