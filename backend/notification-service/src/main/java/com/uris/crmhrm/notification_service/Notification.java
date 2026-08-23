package com.uris.crmhrm.notification_service;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(nullable = false)
    private Long taskId;

    @Column(nullable = false)
    private Instant receivedAt;

    protected Notification() {
    }

    public Notification(String message, Long taskId) {
        this.message = message;
        this.taskId = taskId;
        this.receivedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Long getTaskId() {
        return taskId;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
}
