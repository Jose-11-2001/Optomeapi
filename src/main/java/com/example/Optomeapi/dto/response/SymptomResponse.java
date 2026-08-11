package com.example.Optomeapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SymptomResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
    private List<String> commonCauses;
}
