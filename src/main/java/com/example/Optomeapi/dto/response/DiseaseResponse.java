package com.example.Optomeapi.dto.response;

import com.example.Optomeapi.enums.Severity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
