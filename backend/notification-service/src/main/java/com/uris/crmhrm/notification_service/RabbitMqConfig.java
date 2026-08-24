package com.uris.crmhrm.notification_service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJavaTypeMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "crm.exchange";
    public static final String TASK_CREATED_QUEUE = "task.created.queue";
    public static final String TASK_CREATED_ROUTING_KEY = "task.created";

    @Bean
    TopicExchange crmExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    Queue taskCreatedQueue() {
        return QueueBuilder.durable(TASK_CREATED_QUEUE).build();
    }

    @Bean
    Binding taskCreatedBinding(Queue taskCreatedQueue, TopicExchange crmExchange) {
        return BindingBuilder.bind(taskCreatedQueue).to(crmExchange).with(TASK_CREATED_ROUTING_KEY);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        JacksonJsonMessageConverter converter = new JacksonJsonMessageConverter();
        converter.setTypePrecedence(JacksonJavaTypeMapper.TypePrecedence.INFERRED);
        return converter;
    }
}
