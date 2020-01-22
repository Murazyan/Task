package com.example.task.controller;

import com.example.task.dto.ResponseId;
import com.example.task.model.NumbersData;
import com.example.task.service.GCDService;
import com.example.task.service.NumbersDataService;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/gcd")
public class MainController {

    private GCDService gcdService;
    private NumbersDataService numbersDataService;
    private RabbitTemplate rabbitTemplate;


    @Autowired
    public MainController(GCDService gcdService,
                          NumbersDataService numbersDataService,
                          RabbitTemplate rabbitTemplate){
        this.gcdService = gcdService;
        this.numbersDataService = numbersDataService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/calculate-gcd")
    public ResponseEntity calculate(@RequestBody NumbersData numbersData) throws InterruptedException {
        long gcd = gcdService.calculateGCD(numbersData.getFirst(), numbersData.getSecond());
        NumbersData data = numbersDataService.save(numbersData);
//        rabbitTemplate.convertAndSend(String.valueOf(data.getId()), gcd); //, routingKey,object
//        rabbitTemplate.convertAndSend("gcd", gcd); //, routingKey,object
//        rabbitTemplate.convertAndSend(gcd); //object
//        rabbitTemplate.convertAndSend("exchange",String.valueOf(data.getId()), gcd); //exchange, routingKey,object
        Object o = rabbitTemplate.convertSendAndReceive("gcd", gcd, message -> {  // routingKey, message, messagePostProcessor
            System.out.println("ashxatum e lambda methody");
            long gcd1 = gcdService.calculateGCD(numbersData.getFirst(), numbersData.getSecond());
            return new Message(String.valueOf(gcd1).getBytes(), message.getMessageProperties());
        });
//        System.out.println("objecttt "+o);
        Message gcd1 = rabbitTemplate.receive("gcd");
//        byte[] body = gcd1.getBody();
        System.out.println("body- "+gcd1);
        return ResponseEntity.ok(ResponseId.builder()
        .id(data.getId())
        .build());
    }

    @GetMapping("get-result/{id}")
    public ResponseEntity getGCDResult(@PathVariable("id")int id){
        Message gcd = rabbitTemplate.receive("gcd");
        System.out.println(gcd);
        return ResponseEntity.ok(gcd.getBody());

    }
}
