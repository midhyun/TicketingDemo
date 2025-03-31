package com.example.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "reservation.exchange";
    public static final String QUEUE_NAME = "reservation.queue";
    public static final String ROUTING_KEY = "reservation.routingKey";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue reservationQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Binding reservationBinding(Queue reservationQueue, TopicExchange reservationExchange) {
        return BindingBuilder.bind(reservationQueue)
                .to(reservationExchange)
                .with(ROUTING_KEY);
    }
}
