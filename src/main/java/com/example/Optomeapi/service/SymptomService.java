package com.example.Optomeapi.service;

import com.example.Optomeapi.doctor.dto.request.SymptomRequest;
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

    public SymptomResponse createSymptom(SymptomRequest request) {
        Symptom symptom = new Symptom();
        symptom.setName(request.getName());
        symptom.setDescription(request.getDescription());
        symptom.setType(request.getType());
        symptom.setCommonCauses(request.getCommonCauses());
        Symptom saved = symptomRepository.save(symptom);
        return mapToResponse(saved);
    }

    public SymptomResponse updateSymptom(Long id, SymptomRequest request) {
        Symptom symptom = symptomRepository.findById(id)
            .orElseThrow(() -> new ApiException("Symptom not found", HttpStatus.NOT_FOUND));
        if (request.getName() != null) symptom.setName(request.getName());
        if (request.getDescription() != null) symptom.setDescription(request.getDescription());
        if (request.getType() != null) symptom.setType(request.getType());
        if (request.getCommonCauses() != null) symptom.setCommonCauses(request.getCommonCauses());
        Symptom updated = symptomRepository.save(symptom);
        return mapToResponse(updated);
    }

    public void deleteSymptom(Long id) {
        Symptom symptom = symptomRepository.findById(id)
            .orElseThrow(() -> new ApiException("Symptom not found", HttpStatus.NOT_FOUND));
        symptomRepository.delete(symptom);
    }

    private SymptomResponse mapToResponse(Symptom symptom) {
        SymptomResponse response = new SymptomResponse();
        response.setId(symptom.getId());
        response.setName(symptom.getName());
        response.setDescription(symptom.getDescription());
        response.setType(symptom.getType());
        response.setCommonCauses(symptom.getCommonCauses());
        return response;
    }
}
