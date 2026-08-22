package com.uris.crmhrm.task_service;

import java.time.Instant;

public record Task(Long id, String title, String description, Long employeeId, String status, Instant createdAt) {
}
