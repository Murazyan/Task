package com.example.task.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "gcd")
    public void listen(@Payload String in,  @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        System.out.println("Message read from gcd : " + in+" , key = "+key);
    }

    @RabbitListener(queues = "gcd")
    public void listen(@Payload long in,  @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String key) {
        System.out.println("Message read from gcd : " + in+" , key = "+key);
    }

    @RabbitListener(queues = "gcd")
    public void listen1( String in) {
        System.out.println("Message read from gcd: : " + in);
    }

    @RabbitListener(queues = "gcd")
    public void listen1( long in) {
        System.out.println("Message read from gcd: : " + in);
    }
}
