package com.example.Optomeapi.doctor.dto.request;

import lombok.Data;

@Data
public class SignRequest {
    private String name;
    private String description;
    private String category;
    private String severityIndicator;
}
