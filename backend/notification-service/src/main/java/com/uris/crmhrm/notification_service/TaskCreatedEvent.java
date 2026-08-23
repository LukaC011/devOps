package com.uris.crmhrm.notification_service;

public record TaskCreatedEvent(Long taskId, String title, Long employeeId) {
}
