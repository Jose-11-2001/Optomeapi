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
        Sign sign = new Sign();
        sign.setName(request.getName());
        sign.setDescription(request.getDescription());
        sign.setCategory(request.getCategory());
        sign.setSeverityIndicator(request.getSeverityIndicator());
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
        SignResponse response = new SignResponse();
        response.setId(sign.getId());
        response.setName(sign.getName());
        response.setDescription(sign.getDescription());
        response.setCategory(sign.getCategory());
        response.setSeverityIndicator(sign.getSeverityIndicator());
        return response;
    }
}
