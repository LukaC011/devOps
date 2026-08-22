package com.uris.crmhrm.notification_service;

import java.time.Instant;

public record Notification(Long id, String message, Long taskId, Instant receivedAt) {
}
