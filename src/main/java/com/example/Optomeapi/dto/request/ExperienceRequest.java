package com.example.Optomeapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExperienceRequest {
    @NotBlank
    private String position;
    
    @NotBlank
    private String hospital;
    
    @NotBlank
    private String startDate;
    
    private String endDate;
}