package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.response.SymptomResponse;
import com.example.Optomeapi.entity.Symptom;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SymptomService {

    private final SymptomRepository symptomRepository;

    public List<SymptomResponse> getAllSymptoms() {
        return symptomRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public SymptomResponse getSymptomById(Long id) {
        Symptom symptom = symptomRepository.findById(id)
            .orElseThrow(() -> new ApiException("Symptom not found", HttpStatus.NOT_FOUND));
        return mapToResponse(symptom);
    }

    private SymptomResponse mapToResponse(Symptom symptom) {
        return SymptomResponse.builder()
            .id(symptom.getId())
            .name(symptom.getName())
            .description(symptom.getDescription())
            .type(symptom.getType())
            .commonCauses(symptom.getCommonCauses())
            .build();
    }
}