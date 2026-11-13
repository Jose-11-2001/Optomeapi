package com.example.Optomeapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SymptomReportResponse {
    private Long id;
    private Long patientId;
    private List<SymptomResponse> symptoms;
    private List<SignResponse> signs;
    private LocalDate onsetDate;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}