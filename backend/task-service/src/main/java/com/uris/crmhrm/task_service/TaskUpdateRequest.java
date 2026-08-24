package com.uris.crmhrm.task_service;

public record TaskUpdateRequest(String title, String description, Long employeeId, TaskStatus status) {
}
