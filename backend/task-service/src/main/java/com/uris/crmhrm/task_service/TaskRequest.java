package com.uris.crmhrm.task_service;

public record TaskRequest(String title, String description, Long employeeId) {
}
