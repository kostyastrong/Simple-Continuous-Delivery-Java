package org.example.outbox;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfiguration {
    public static final String QUEUE_NAME_NEW = "to_create";

    @Bean
    public Queue queue1() {
        return new Queue(QUEUE_NAME_NEW, false);
    }
}


