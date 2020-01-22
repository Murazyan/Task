package com.example.task.service.impl;

import com.example.task.model.NumbersData;
import com.example.task.repository.NumbersDataRepository;
import com.example.task.service.NumbersDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NumbersDataSericeImpl implements NumbersDataService {
    
    private NumbersDataRepository numbersDataRepository;
    
    @Autowired
    public NumbersDataSericeImpl(NumbersDataRepository numbersDataRepository){
        this.numbersDataRepository = numbersDataRepository;
    }


    @Override
    public NumbersData save(NumbersData data) {
        return numbersDataRepository.save(data);
    }
}
