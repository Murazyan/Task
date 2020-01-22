package com.example.task.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitListenerr {

    @RabbitListener(queues="gcd")
    public void listen(byte[] message) {
        String msg = new String(message);

        System.out.println("Received a new notification...");
        System.out.println(msg.toString());
    }
}
