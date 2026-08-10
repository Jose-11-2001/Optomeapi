package com.example.Optomeapi.dto.request;
import com.example.Optomeapi.enum.Severity;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class DiseaseRequest {
    @NotBlank
    private String name;
    
    private String description;
    
    private List<Long> symptomIds;
    
    private List<Long> signIds;
    
    private Severity severity;
    
    private String treatment;
    
    private List<String> riskFactors;
}