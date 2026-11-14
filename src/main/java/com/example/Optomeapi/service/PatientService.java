package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.request.BookAppointmentRequest;
import com.example.Optomeapi.dto.request.CreateSymptomReportRequest;
import com.example.Optomeapi.dto.request.DiagnoseRequest;
import com.example.Optomeapi.dto.request.PatientRequest;
import com.example.Optomeapi.dto.request.UpdateSymptomReportRequest;
import com.example.Optomeapi.dto.response.AppointmentResponse;
import com.example.Optomeapi.dto.response.DiseaseResponse;
import com.example.Optomeapi.dto.response.DiagnosisResponse;
import com.example.Optomeapi.dto.response.PatientResponse;
import com.example.Optomeapi.dto.response.PrescriptionResponse;
import com.example.Optomeapi.dto.response.SymptomReportResponse;
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

    // ---- CRUD ----
    public PatientResponse createPatient(PatientRequest request) {
        Patient patient = mapToEntity(request);
        return mapToResponse(patientRepository.save(patient));
    }

    public PatientResponse getPatientById(Long id) {
        return mapToResponse(findPatient(id));
    }

    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        Patient patient = findPatient(id);
        patient.setFirstName(request.getFirstName());
        patient.setLastName(request.getLastName());
        patient.setEmail(request.getEmail());
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        return mapToResponse(patientRepository.save(patient));
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Patient not found with id: " + id);
        }
        patientRepository.deleteById(id);
    }

    // ---- profile aliases ----
    public PatientResponse getProfile(Long patientId) {
        return getPatientById(patientId);
    }

    public PatientResponse updateProfile(Long patientId, PatientRequest request) {
        return updatePatient(patientId, request);
    }

    // ---- JWT -> patient id resolution ----
    public Long findIdByUserEmail(String email) {
        return patientRepository.findByUser_Email(email)
                .orElseThrow(() -> new RuntimeException(
                        "Patient profile not found for user: " + email))
                .getId();
    }

    // ---- appointments ----
    public List<AppointmentResponse> getAppointments(Long patientId) {
        return List.of();
    }

    public AppointmentResponse bookAppointment(Long patientId, BookAppointmentRequest request) {
        return null;
    }

    public void cancelAppointment(Long appointmentId) {
    }

    // ---- prescriptions ----
    public List<PrescriptionResponse> getPrescriptions(Long patientId) {
        return List.of();
    }

    // ---- diseases / diagnosis ----
    public List<DiseaseResponse> getDiseases() {
        return List.of();
    }

    public DiagnosisResponse runDiagnose(DiagnoseRequest request) {
        return null;
    }

    // ---- symptom reports ----
    public List<SymptomReportResponse> getSymptomReports(Long patientId) {
        return List.of();
    }

    public SymptomReportResponse createSymptomReport(CreateSymptomReportRequest request) {
        return null;
    }

    public SymptomReportResponse updateSymptomReport(Long reportId, UpdateSymptomReportRequest request) {
        return null;
    }

    public void deleteSymptomReport(Long reportId) {
    }

    // ---- helpers ----
    private Patient findPatient(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + id));
    }

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
        return new PatientResponse(
                patient.getId(),
                patient.getFirstName(),
                patient.getLastName(),
                patient.getEmail(),
                patient.getPhoneNumber(),
                patient.getDateOfBirth(),
                patient.getGender());
    }
}