package com.example.task.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

//    @RabbitListener(queues = "gcd")
//    public void listen(String in) {
//        System.out.println("Message read from myQueue : " + in);
//    }
}
