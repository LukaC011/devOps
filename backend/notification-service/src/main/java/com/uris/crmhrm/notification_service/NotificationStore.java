package com.uris.crmhrm.notification_service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class NotificationStore {

    private final List<Notification> notifications = new CopyOnWriteArrayList<>();
    private final AtomicLong idSequence = new AtomicLong();

    public Notification add(String message, Long taskId) {
        Notification notification = new Notification(idSequence.incrementAndGet(), message, taskId, Instant.now());
        notifications.add(notification);
        return notification;
    }

    public List<Notification> findAll() {
        return List.copyOf(notifications);
    }
}
