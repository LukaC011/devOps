package com.uris.crmhrm.task_service;

public record TaskCreatedEvent(Long taskId, String title, Long employeeId) {
}
