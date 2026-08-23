package com.uris.crmhrm.task_service;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@ExtendWith(MockitoExtension.class)
class TaskEventPublisherTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private TaskEventPublisher taskEventPublisher;

    @Test
    void sendsTaskCreatedEventToTheTopicExchange() {
        Task task = new Task("Pripremi ponudu", "Ponuda za Delta", 1L);

        taskEventPublisher.publishTaskCreated(task);

        verify(rabbitTemplate).convertAndSend(RabbitMqConfig.EXCHANGE_NAME,
                RabbitMqConfig.TASK_CREATED_ROUTING_KEY,
                new TaskCreatedEvent(task.getId(), "Pripremi ponudu", 1L));
    }
}
