package com.example.Optomeapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosisResponse {
    private Long patientId;
    private List<DiseaseResponse> possibleDiseases;
    private List<String> matchedSymptoms;
    private List<String> matchedSigns;
    private String recommendation;
    private String severity;
}