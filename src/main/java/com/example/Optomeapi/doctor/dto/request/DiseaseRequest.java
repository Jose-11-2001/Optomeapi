package com.example.Optomeapi.doctor.dto.request;

import com.example.Optomeapi.enums.Severity;
import lombok.Data;
import java.util.List;

@Data
public class DiseaseRequest {
    private String name;
    private String description;
    private List<Long> symptomIds;
    private List<Long> signIds;
    private Severity severity;
    private String treatment;
    private List<String> riskFactors;
}
