package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.request.PatientRequest;
import com.example.Optomeapi.dto.response.PatientResponse;
import com.example.Optomeapi.entity.Patient;
import com.example.Optomeapi.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // === Core CRUD ===

    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = mapToEntity(request);
        Patient savedPatient = patientRepository.save(patient);
        return mapToResponse(savedPatient);
    }

    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
        return mapToResponse(patient);
    }

    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));

        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());

        Patient updatedPatient = patientRepository.save(patient);
        return mapToResponse(updatedPatient);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    // === Profile endpoints (delegating to CRUD) ===

    public PatientResponse getProfile(Long patientId) {
        return getPatientById(patientId);
    }

    public PatientResponse updateProfile(Long patientId, PatientRequest request) {
        return updatePatient(patientId, request);
    }

    // === Appointments — placeholders until AppointmentService is wired ===

    public List<Object> getAppointments(Long patientId) {
        // TODO: delegate to AppointmentService
        return Collections.emptyList();
    }

    public String bookAppointment(Long patientId, Object request) {
        // TODO: delegate to AppointmentService
        return "Appointment booking not yet implemented";
    }

    public String cancelAppointment(Long appointmentId) {
        // TODO: delegate to AppointmentService
        return "Appointment cancellation not yet implemented";
    }

    // === Prescriptions — placeholder ===

    public List<Object> getPrescriptions(Long patientId) {
        // TODO: delegate to PrescriptionService
        return Collections.emptyList();
    }

    // === Diseases — placeholder ===

    public List<Object> getDiseases() {
        // TODO: delegate to DiseaseService
        return Collections.emptyList();
    }

    // === AI Diagnosis — placeholder ===

    public String runDiagnose(Object request) {
        // TODO: implement AI diagnosis
        return "Diagnosis not yet implemented";
    }

    // === Symptom Reports — placeholder ===

    public List<Object> getSymptomReports(Long patientId) {
        // TODO: delegate to SymptomService
        return Collections.emptyList();
    }

    public String createSymptomReport(Object request) {
        // TODO: delegate to SymptomService
        return "Symptom report creation not yet implemented";
    }

    public String updateSymptomReport(Long id, Object request) {
        // TODO: delegate to SymptomService
        return "Symptom report update not yet implemented";
    }

    public String deleteSymptomReport(Long id) {
        // TODO: delegate to SymptomService
        return "Symptom report deletion not yet implemented";
    }

    // === Mapping helpers ===

    private Patient mapToEntity(PatientRequest dto) {
        Patient patient = new Patient();
        patient.setFirstName(dto.getFirstName());
        patient.setLastName(dto.getLastName());
        patient.setEmail(dto.getEmail());
        patient.setPhoneNumber(dto.getPhoneNumber());
        patient.setDateOfBirth(dto.getDateOfBirth());
        patient.setGender(dto.getGender());
        return patient;
    }

    private PatientResponse mapToResponse(Patient patient) {
        PatientResponse r = new PatientResponse();
        r.setId(patient.getId());
        r.setFirstName(patient.getFirstName());
        r.setLastName(patient.getLastName());
        r.setEmail(patient.getEmail());
        r.setPhoneNumber(patient.getPhoneNumber());
        r.setDateOfBirth(patient.getDateOfBirth());
        r.setGender(patient.getGender());
        return r;
    }
}