package com.example.Optomeapi.service;

import com.example.Optomeapi.doctor.dto.request.SignRequest;
import com.example.Optomeapi.dto.response.SignResponse;
import com.example.Optomeapi.entity.Sign;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.SignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SignService {

    private final SignRepository signRepository;

    public List<SignResponse> getAllSigns() {
        return signRepository.findAll().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public SignResponse getSignById(Long id) {
        Sign sign = signRepository.findById(id)
            .orElseThrow(() -> new ApiException("Sign not found", HttpStatus.NOT_FOUND));
        return mapToResponse(sign);
    }

    public SignResponse createSign(SignRequest request) {
        Sign sign = Sign.builder()
            .name(request.getName())
            .description(request.getDescription())
            .category(request.getCategory())
            .severityIndicator(request.getSeverityIndicator())
            .build();
        Sign saved = signRepository.save(sign);
        return mapToResponse(saved);
    }

    public SignResponse updateSign(Long id, SignRequest request) {
        Sign sign = signRepository.findById(id)
            .orElseThrow(() -> new ApiException("Sign not found", HttpStatus.NOT_FOUND));
        if (request.getName() != null) sign.setName(request.getName());
        if (request.getDescription() != null) sign.setDescription(request.getDescription());
        if (request.getCategory() != null) sign.setCategory(request.getCategory());
        if (request.getSeverityIndicator() != null) sign.setSeverityIndicator(request.getSeverityIndicator());
        Sign updated = signRepository.save(sign);
        return mapToResponse(updated);
    }

    public void deleteSign(Long id) {
        Sign sign = signRepository.findById(id)
            .orElseThrow(() -> new ApiException("Sign not found", HttpStatus.NOT_FOUND));
        signRepository.delete(sign);
    }

    private SignResponse mapToResponse(Sign sign) {
        return SignResponse.builder()
            .id(sign.getId())
            .name(sign.getName())
            .description(sign.getDescription())
            .category(sign.getCategory())
            .severityIndicator(sign.getSeverityIndicator())
            .build();
    }
}
