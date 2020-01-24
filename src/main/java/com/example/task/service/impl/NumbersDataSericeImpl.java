package com.example.task.service.impl;

import com.example.task.dto.GCDResponse;
import com.example.task.model.NumbersData;
import com.example.task.model.enums.ResponseStatus;
import com.example.task.repository.NumbersDataRepository;
import com.example.task.service.NumbersDataService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class NumbersDataSericeImpl implements NumbersDataService {

    private NumbersDataRepository numbersDataRepository;

    @Autowired
    public NumbersDataSericeImpl(NumbersDataRepository numbersDataRepository) {
        this.numbersDataRepository = numbersDataRepository;
    }


    @Override
    public NumbersData save(NumbersData data) {
        return numbersDataRepository.save(data);
    }

    @Override
    public GCDResponse getResultById(int id) {
        NumbersData numbersData = numbersDataRepository.findById(id).orElse(null);
        GCDResponse result = GCDResponse.builder()
                .error("Data with id = " + id + " not found")
                .id(id)
                .status(ResponseStatus.ERROR)
                .build();
        if (numbersData != null) {
            result = numbersData.getResult() == 0 ? GCDResponse.builder()
                    .error("Data with id = " + id + " not not completed yet")
                    .id(id)
                    .status(ResponseStatus.NOT_COMPLETED)
                    .build()
                    :
                    GCDResponse.builder()
                            .error("")
                            .id(id)
                            .result(numbersData.getResult())
                            .status(ResponseStatus.COMPLETED)
                            .build();
        }
        return result;
    }
}
