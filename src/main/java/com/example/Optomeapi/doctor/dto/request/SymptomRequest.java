package com.example.Optomeapi.doctor.dto.request;

import lombok.Data;
import java.util.List;

@Data
public class SymptomRequest {
    private String name;
    private String description;
    private String type;
    private List<String> commonCauses;
}
