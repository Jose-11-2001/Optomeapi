package com.example.Optomeapi.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignRequest {
    @NotBlank
    private String name;
    
    private String description;
    
    private String category;
    
    private String severityIndicator;
}
