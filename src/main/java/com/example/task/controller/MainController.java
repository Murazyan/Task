package com.example.task.controller;

import com.example.task.dto.ResponseId;
import com.example.task.model.NumbersData;
import com.example.task.service.GCDService;
import com.example.task.service.NumbersDataService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity calculate(@RequestBody NumbersData numbersData){
        long gcd = gcdService.calculateGCD(numbersData.getFirst(), numbersData.getSecond());
        NumbersData data = numbersDataService.save(numbersData);
        rabbitTemplate.convertAndSend(String.valueOf(data.getId()),"gcd", gcd); //exchange, routingKey,object

        Message gcd1 = rabbitTemplate.receive("gcd");
//        byte[] body = gcd1.getBody();
        System.out.println("body- "+gcd1);
        return ResponseEntity.ok(ResponseId.builder()
        .id(data.getId())
        .build());
    }
}
