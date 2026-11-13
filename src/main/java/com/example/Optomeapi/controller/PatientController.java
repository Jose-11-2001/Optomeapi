package com.example.Optomeapi.controller;

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
import com.example.Optomeapi.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // === PROFILE ENDPOINTS ===
    @GetMapping("/profile")
    public ResponseEntity<PatientResponse> getProfile(@RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getProfile(patientId));
    }

    @PutMapping("/profile")
    public ResponseEntity<PatientResponse> updateProfile(
            @RequestHeader("X-Patient-Id") Long patientId,
            @RequestBody PatientRequest request) {
        return ResponseEntity.ok(patientService.updateProfile(patientId, request));
    }

    // === APPOINTMENT ENDPOINTS ===
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointments(
            @RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getAppointments(patientId));
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            @RequestHeader("X-Patient-Id") Long patientId,
            @RequestBody BookAppointmentRequest request) {
        return ResponseEntity.ok(patientService.bookAppointment(patientId, request));
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<String> getAppointmentDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Appointment details for ID: " + id);
    }

    @PutMapping("/appointments/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        patientService.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    // === PRESCRIPTION ENDPOINTS ===
    @GetMapping("/prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptions(
            @RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getPrescriptions(patientId));
    }

    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<String> getPrescriptionDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Prescription details for ID: " + id);
    }

    // === DISEASE ENDPOINTS ===
    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseResponse>> getDiseases() {
        return ResponseEntity.ok(patientService.getDiseases());
    }

    @GetMapping("/diseases/{id}")
    public ResponseEntity<String> getDiseaseDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Disease details for ID: " + id);
    }

    // === AI DIAGNOSIS ENDPOINT ===
    @PostMapping("/diagnose")
    public ResponseEntity<DiagnosisResponse> runDiagnose(@RequestBody DiagnoseRequest request) {
        return ResponseEntity.ok(patientService.runDiagnose(request));
    }

    // === SYMPTOM REPORT ENDPOINTS ===
    @GetMapping("/symptom-reports")
    public ResponseEntity<List<SymptomReportResponse>> getSymptomReports(
            @RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getSymptomReports(patientId));
    }

    @PostMapping("/symptom-reports")
    public ResponseEntity<SymptomReportResponse> createSymptomReport(
            @RequestBody CreateSymptomReportRequest request) {
        return ResponseEntity.ok(patientService.createSymptomReport(request));
    }

    @GetMapping("/symptom-reports/{id}")
    public ResponseEntity<String> getSymptomReportDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Symptom report details for ID: " + id);
    }

    @PutMapping("/symptom-reports/{id}")
    public ResponseEntity<SymptomReportResponse> updateSymptomReport(
            @PathVariable Long id,
            @RequestBody UpdateSymptomReportRequest request) {
        return ResponseEntity.ok(patientService.updateSymptomReport(id, request));
    }

    @DeleteMapping("/symptom-reports/{id}")
    public ResponseEntity<Void> deleteSymptomReport(@PathVariable Long id) {
        patientService.deleteSymptomReport(id);
        return ResponseEntity.noContent().build();
    }
}