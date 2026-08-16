package com.example.Optomeapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SymptomResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
    private List<String> commonCauses;
}
