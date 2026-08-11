package com.example.Optomeapi.dto.response;

import com.example.Optomeapi.enums.Severity;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DiseaseResponse {
    private Long id;
    private String name;
    private String description;
    private List<String> symptoms;
    private List<String> signs;
    private Severity severity;
    private String treatment;
    private List<String> riskFactors;
}
