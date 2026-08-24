package com.uris.crmhrm.task_service;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String EXCHANGE_NAME = "crm.exchange";
    public static final String TASK_CREATED_ROUTING_KEY = "task.created";

    @Bean
    TopicExchange crmExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
