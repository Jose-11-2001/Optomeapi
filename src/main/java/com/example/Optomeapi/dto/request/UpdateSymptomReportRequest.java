package com.example.Optomeapi.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateSymptomReportRequest {
    private List<Long> symptomIds;
    private List<Long> signIds;
    private LocalDate onsetDate;
    private String description;
    private String status;
}