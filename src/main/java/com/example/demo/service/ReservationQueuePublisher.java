package com.example.demo.service;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.ReservationRequestDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationQueuePublisher {

    private final RabbitTemplate rabbitTemplate;

    public ReservationQueuePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishReservationRequest(ReservationRequestDto requestDto) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE_NAME,
                RabbitMQConfig.ROUTING_KEY,
                requestDto
        );
    }
}
