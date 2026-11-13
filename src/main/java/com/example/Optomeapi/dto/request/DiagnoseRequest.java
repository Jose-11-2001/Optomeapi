package com.example.Optomeapi.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class DiagnoseRequest {
    @NotNull
    private Long patientId;

    private List<Long> symptomIds;
    private List<Long> signIds;

    private String additionalNotes;
}