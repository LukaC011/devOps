package com.uris.crmhrm.notification_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TaskCreatedListener {

    private static final Logger log = LoggerFactory.getLogger(TaskCreatedListener.class);

    private final NotificationService notificationService;

    public TaskCreatedListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMqConfig.TASK_CREATED_QUEUE)
    public void onTaskCreated(TaskCreatedEvent event) {
        Notification notification = notificationService.record(
                "Kreiran je zadatak: " + event.title(), event.taskId());
        log.info("Stored notification {} for task {}", notification.getId(), event.taskId());
    }
}
