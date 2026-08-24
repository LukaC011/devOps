package com.uris.crmhrm.notification_service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Transactional
    public Notification record(String message, Long taskId) {
        return notificationRepository.save(new Notification(message, taskId));
    }
}
