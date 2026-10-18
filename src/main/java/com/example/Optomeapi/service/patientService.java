package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.request.PatientRequest;
import com.example.Optomeapi.dto.response.PatientResponse;
import com.example.Optomeapi.entity.Patient;
import com.example.Optomeapi.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Create a new patient
    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = mapToEntity(request);
        Patient savedPatient = patientRepository.save(patient);
        return mapToResponseDto(savedPatient);
    }

    // Get patient by ID
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return mapToResponse(patient);
    }

    // Get all patients
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Update patient
    public PatientResponseDto updatePatient(Long id, PatientRequestDto requestDto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        patient.setFirstName(requestDto.getFirstName());
        patient.setLastName(requestDto.getLastName());
        patient.setEmail(requestDto.getEmail());
        patient.setPhoneNumber(requestDto.getPhoneNumber());
        patient.setDateOfBirth(requestDto.getDateOfBirth());
        patient.setGender(requestDto.getGender());

        Patient updatedPatient = patientRepository.save(patient);
        return mapToResponseDto(updatedPatient);
    }

    // Delete patient
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    // --- Helper Mapping Methods ---

    private Patient mapToEntity(PatientRequestDto dto) {
        Patient patient = new Patient();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        return patient;
    }

    private PatientResponseDto mapToResponseDto(Patient patient) {
        return new PatientResponseDto(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPhoneNumber(),
                patient.getDateOfBirth(),
                patient.getGender()
        );
    }
}
