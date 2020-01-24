package com.example.task.service;

import com.example.task.dto.GCDResponse;
import com.example.task.model.NumbersData;

public interface NumbersDataService {

    NumbersData save(NumbersData data);

    GCDResponse getResultById(int id);
}
