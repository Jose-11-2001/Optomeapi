package com.example.Optomeapi.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class SymptomRequest {
    @NotBlank
    private String name;
    
    private String description;
    
    private String type;
    
    private List<String> commonCauses;
}

