package com.example.Optomeapi.service;

import com.example.Optomeapi.doctor.dto.request.PrescriptionRequest;
import com.example.Optomeapi.dto.response.PrescriptionResponse;
import com.example.Optomeapi.entity.Doctor;
import com.example.Optomeapi.entity.Patient;
import com.example.Optomeapi.entity.Prescription;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.DoctorRepository;
import com.example.Optomeapi.repository.PatientRepository;
import com.example.Optomeapi.repository.PrescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public List<PrescriptionResponse> getDoctorPrescriptions(Long doctorId) {
        return prescriptionRepository.findByDoctorId(doctorId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public PrescriptionResponse createPrescription(Long doctorId, PrescriptionRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        Patient patient = patientRepository.findById(request.getPatientId())
            .orElseThrow(() -> new ApiException("Patient not found", HttpStatus.NOT_FOUND));

        Prescription prescription = Prescription.builder()
            .patient(patient)
            .doctor(doctor)
            .date(LocalDate.now())
            .medications(request.getMedications())
            .diagnosis(request.getDiagnosis())
            .notes(request.getNotes())
            .isActive(true)
            .build();
        Prescription saved = prescriptionRepository.save(prescription);
        return mapToResponse(saved);
    }

    public PrescriptionResponse getPrescriptionDetails(Long doctorId, Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
            .orElseThrow(() -> new ApiException("Prescription not found", HttpStatus.NOT_FOUND));
        if (!prescription.getDoctor().getId().equals(doctorId)) {
            throw new ApiException("Access denied", HttpStatus.FORBIDDEN);
        }
        return mapToResponse(prescription);
    }

    public PrescriptionResponse updatePrescription(Long doctorId, Long prescriptionId, PrescriptionRequest request) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
            .orElseThrow(() -> new ApiException("Prescription not found", HttpStatus.NOT_FOUND));
        if (!prescription.getDoctor().getId().equals(doctorId)) {
            throw new ApiException("Access denied", HttpStatus.FORBIDDEN);
        }
        if (request.getMedications() != null) prescription.setMedications(request.getMedications());
        if (request.getDiagnosis() != null) prescription.setDiagnosis(request.getDiagnosis());
        if (request.getNotes() != null) prescription.setNotes(request.getNotes());
        Prescription updated = prescriptionRepository.save(prescription);
        return mapToResponse(updated);
    }

    public void deletePrescription(Long doctorId, Long prescriptionId) {
        Prescription prescription = prescriptionRepository.findById(prescriptionId)
            .orElseThrow(() -> new ApiException("Prescription not found", HttpStatus.NOT_FOUND));
        if (!prescription.getDoctor().getId().equals(doctorId)) {
            throw new ApiException("Access denied", HttpStatus.FORBIDDEN);
        }
        prescriptionRepository.delete(prescription);
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
