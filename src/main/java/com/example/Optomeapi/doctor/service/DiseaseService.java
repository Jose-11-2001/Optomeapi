package com.example.Optomeapi.doctor.service;

import com.example.Optomeapi.doctor.dto.request.DiseaseRequest;
import com.example.Optomeapi.dto.response.DiseaseResponse;
import com.example.Optomeapi.entity.Disease;
import com.example.Optomeapi.entity.Sign;
import com.example.Optomeapi.entity.Symptom;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.DiseaseRepository;
import com.example.Optomeapi.repository.SignRepository;
import com.example.Optomeapi.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiseaseService {

    private final DiseaseRepository diseaseRepository;
    private final SymptomRepository symptomRepository;
    private final SignRepository signRepository;

    public List<DiseaseResponse> getAllDiseases() {
        return diseaseRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public DiseaseResponse getDiseaseById(Long id) {
        Disease disease = diseaseRepository.findById(id)
            .orElseThrow(() -> new ApiException("Disease not found", HttpStatus.NOT_FOUND));
        return mapToResponse(disease);
    }

    @Transactional
    public DiseaseResponse createDisease(DiseaseRequest request) {
        if (diseaseRepository.existsByName(request.getName())) {
            throw new ApiException("Disease already exists", HttpStatus.CONFLICT);
        }

        Disease disease = new Disease();
        disease.setName(request.getName());
        disease.setDescription(request.getDescription());
        disease.setSeverity(request.getSeverity());
        disease.setTreatment(request.getTreatment());
        disease.setRiskFactors(request.getRiskFactors());

        if (request.getSymptomIds() != null) {
            List<Symptom> symptoms = symptomRepository.findAllById(request.getSymptomIds());
            disease.setSymptoms(symptoms);
        }

        if (request.getSignIds() != null) {
            List<Sign> signs = signRepository.findAllById(request.getSignIds());
            disease.setSigns(signs);
        }

        Disease saved = diseaseRepository.save(disease);
        return mapToResponse(saved);
    }

    @Transactional
    public DiseaseResponse updateDisease(Long id, DiseaseRequest request) {
        Disease disease = diseaseRepository.findById(id)
            .orElseThrow(() -> new ApiException("Disease not found", HttpStatus.NOT_FOUND));

        if (request.getName() != null && !request.getName().equals(disease.getName())) {
            if (diseaseRepository.existsByName(request.getName())) {
                throw new ApiException("Disease name already exists", HttpStatus.CONFLICT);
            }
            disease.setName(request.getName());
        }

        if (request.getDescription() != null) disease.setDescription(request.getDescription());
        if (request.getSeverity() != null) disease.setSeverity(request.getSeverity());
        if (request.getTreatment() != null) disease.setTreatment(request.getTreatment());
        if (request.getRiskFactors() != null) disease.setRiskFactors(request.getRiskFactors());

        if (request.getSymptomIds() != null) {
            List<Symptom> symptoms = symptomRepository.findAllById(request.getSymptomIds());
            disease.setSymptoms(symptoms);
        }

        if (request.getSignIds() != null) {
            List<Sign> signs = signRepository.findAllById(request.getSignIds());
            disease.setSigns(signs);
        }

        Disease updated = diseaseRepository.save(disease);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteDisease(Long id) {
        Disease disease = diseaseRepository.findById(id)
            .orElseThrow(() -> new ApiException("Disease not found", HttpStatus.NOT_FOUND));
        diseaseRepository.delete(disease);
    }

    private DiseaseResponse mapToResponse(Disease disease) {
        DiseaseResponse response = new DiseaseResponse();
        response.setId(disease.getId());
        response.setName(disease.getName());
        response.setDescription(disease.getDescription());
        response.setSymptoms(disease.getSymptoms().stream()
            .map(Symptom::getName)
            .collect(Collectors.toList()));
        response.setSigns(disease.getSigns().stream()
            .map(Sign::getName)
            .collect(Collectors.toList()));
        response.setSeverity(disease.getSeverity());
        response.setTreatment(disease.getTreatment());
        response.setRiskFactors(disease.getRiskFactors());
        return response;
    }
}