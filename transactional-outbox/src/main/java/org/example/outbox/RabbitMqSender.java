package org.example.outbox;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(UserEntity user) {
        rabbitTemplate.convertAndSend("to_create", user.toJson());
    }
}
