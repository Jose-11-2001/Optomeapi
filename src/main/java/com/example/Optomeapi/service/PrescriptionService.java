package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.response.PrescriptionResponse;
import com.example.Optomeapi.entity.Prescription;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;

    public List<PrescriptionResponse> getDoctorPrescriptions(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    private PrescriptionResponse mapToResponse(Prescription prescription) {
        return PrescriptionResponse.builder()
            .id(prescription.getId())
            .doctorId(prescription.getDoctor().getId())
            .doctorName(prescription.getDoctor().getUser().getFirstName() + " " + 
                       prescription.getDoctor().getUser().getLastName())
            .date(prescription.getDate())
            .medications(prescription.getMedications())
            .diagnosis(prescription.getDiagnosis())
            .notes(prescription.getNotes())
            .isActive(prescription.isActive())
            .build();
    }
}