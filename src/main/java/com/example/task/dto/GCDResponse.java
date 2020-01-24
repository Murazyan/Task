package com.example.task.dto;

import com.example.task.model.enums.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GCDResponse {

    private long id;

    private long result;

    private String error;

    private ResponseStatus status;
}
