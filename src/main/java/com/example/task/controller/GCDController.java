package com.example.task.controller;

import com.example.task.dto.GCDResponse;
import com.example.task.dto.ResponseId;
import com.example.task.model.NumbersData;
import com.example.task.model.enums.ResponseStatus;
import com.example.task.service.NumbersDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gcd")
public class GCDController {

    private NumbersDataService numbersDataService;
    private RabbitTemplate rabbitTemplate;


    @Autowired
    public GCDController(NumbersDataService numbersDataService,
                         RabbitTemplate rabbitTemplate) {
        this.numbersDataService = numbersDataService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/calculate-gcd")
    public ResponseEntity calculate(@RequestBody NumbersData numbersData) throws InterruptedException {
        System.out.println("caluclating");
        NumbersData data = numbersDataService.save(numbersData);
        rabbitTemplate.convertAndSend("gcd.exchange", "gcd.rout.key", data);
        return ResponseEntity.ok(ResponseId.builder()
                .id(data.getId())
                .build());
    }

    @GetMapping("/get-result/{id}")
    public ResponseEntity getGCDResult(@PathVariable("id") int id) {
        GCDResponse responseBody = numbersDataService.getResultById(id);
        HttpStatus httpStatus = HttpStatus.OK;
        if(responseBody.getStatus()== ResponseStatus.ERROR) {
            httpStatus= HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(httpStatus).body(responseBody);

    }
}
