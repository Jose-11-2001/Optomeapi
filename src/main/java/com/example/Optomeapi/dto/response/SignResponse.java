package com.example.Optomeapi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String severityIndicator;
}
