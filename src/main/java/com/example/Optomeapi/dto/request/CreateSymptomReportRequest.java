package com.example.Optomeapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateSymptomReportRequest {
    @NotNull
    private Long patientId;

    @NotNull
    private List<Long> symptomIds;

    private List<Long> signIds;

    private LocalDate onsetDate;
    private String description;
}