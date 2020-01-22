package com.example.task.service.impl;

import com.example.task.service.GCDService;
import org.springframework.stereotype.Service;

@Service
public class GCDServiceImpl  implements GCDService {

    @Override
    public long calculateGCD(long firstNumber, long secondNumber) {
        if (secondNumber != 0)
            return calculateGCD(secondNumber, firstNumber % secondNumber);
        else
            return firstNumber;
    }
}
