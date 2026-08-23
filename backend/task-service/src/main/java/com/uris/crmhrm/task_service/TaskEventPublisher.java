package com.uris.crmhrm.task_service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TaskEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(TaskEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public TaskEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishTaskCreated(Task task) {
        TaskCreatedEvent event = new TaskCreatedEvent(task.getId(), task.getTitle(), task.getEmployeeId());
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_NAME, RabbitMqConfig.TASK_CREATED_ROUTING_KEY, event);
        log.info("Published {} event for task {}", RabbitMqConfig.TASK_CREATED_ROUTING_KEY, task.getId());
    }
}
