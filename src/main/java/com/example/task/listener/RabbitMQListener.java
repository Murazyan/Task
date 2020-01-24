package com.example.task.listener;

import com.example.task.model.NumbersData;
import com.example.task.service.GCDService;
import com.example.task.service.NumbersDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RabbitMQListener {

    private GCDService gcdService;
    private NumbersDataService numbersDataService;


    public RabbitMQListener(GCDService gcdService,
                            NumbersDataService numbersDataService){
        this.numbersDataService = numbersDataService;
        this.gcdService = gcdService;
    }

    @RabbitListener(queues = "gcd.queue")
    public void rabbitListener(@Payload NumbersData numbersData){
        long result = gcdService.calculateGCD(numbersData.getFirst(), numbersData.getSecond());
        numbersData.setResult(result);
        numbersDataService.save(numbersData);
        log.info("new numbers data :"+numbersData);
    }
}
