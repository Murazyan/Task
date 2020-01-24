package com.example.task.controller;

import com.example.task.dto.GCDResponse;
import com.example.task.dto.ResponseId;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Objects;


@Slf4j
@RunWith(SpringRunner.class)
@Test
public class GCDControllerTestNG extends AbstractTestNGSpringContextTests {

    private RestTemplate restTemplate;

    @BeforeTest
    public void beforeTest() {
        logger.info("Creating RestTemplate object before tests");
        this.restTemplate = new RestTemplate();
    }



    @Test
    public void calculateMethodTest()  {
        String calculateGcdURI = "http://localhost:8080/gcd/calculate-gcd";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        logger.info("Add URL :"+calculateGcdURI);
        String jsonBody = "{\"first\":36,\"second\":27}";
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        ResponseEntity<ResponseId> response = this.restTemplate.postForEntity(calculateGcdURI, entity, ResponseId.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        Assert.assertEquals(Objects.requireNonNull(response.getBody()).getId(), 0);
    }


    @Test
    public void getGCDResultMethodTest()  {
        String gcdResultURI = "http://localhost:8080/gcd/get-result/0";
        logger.info("gcdResultURI URL :"+gcdResultURI);
        ResponseEntity<GCDResponse> response = this.restTemplate.getForEntity(gcdResultURI, GCDResponse.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertTrue(Objects.requireNonNull(response.getBody()).getId()>0);
    }


}
