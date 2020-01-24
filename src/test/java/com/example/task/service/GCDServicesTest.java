package com.example.task.service;

import com.example.task.model.NumbersData;
import com.example.task.repository.NumbersDataRepository;
import com.example.task.service.impl.GCDServiceImpl;
import com.example.task.service.impl.NumbersDataSericeImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Test
@Slf4j
@RunWith(SpringRunner.class)
public class GCDServicesTest extends AbstractTestNGSpringContextTests {


    @TestConfiguration
    static class GCDServiceTestContextConfiguration {

        private NumbersDataRepository numbersDataRepository;

        @Bean
        public GCDService gcdService() {
            return new GCDServiceImpl();
        }

        @Bean
        public NumbersDataService numbersDataService() {
            numbersDataRepository = Mockito.mock(NumbersDataRepository.class);
            log.info("creating numbersDataService bean for testing");
            return new NumbersDataSericeImpl(numbersDataRepository);
        }

        @Bean
        public NumbersDataRepository numbersDataRepository() {
            return numbersDataRepository;
        }


    }

    @Autowired
    private NumbersDataRepository numbersDataRepository;

    @Autowired
    private GCDService gcdService;

    @Autowired
    private NumbersDataService numbersDataService;


    @BeforeMethod(description = "mock numbersDataRepository save method")
    public void setUp() {
        when(numbersDataRepository.save(any(NumbersData.class))).thenReturn(NumbersData.builder().id(10).build());
    }


    @Test()
    void calculateGCDTest() {
        long gcd = gcdService.calculateGCD(25, 15);
        Assert.assertEquals(gcd, 5);
        log.info("gcdService: calculateGCD method test: success");
    }

    @Test(description = "numbersDataService save method test")
    public void saveMethodTest() {
        NumbersData savedData = numbersDataService.save(NumbersData.builder()
                .first(36)
                .second(27)
                .build());
        Assert.assertTrue(savedData.getId() > 0);
		log.info("numbersDataService: save method test: success");
    }

}