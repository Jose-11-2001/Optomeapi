package com.example.Optomeapi.controller;

import com.example.Optomeapi.dto.request.PatientRequestDto;
import com.example.Optomeapi.dto.response.PatientResponseDto;
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
    public ResponseEntity<PatientResponseDto> getProfile(@RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getProfile(patientId));
    }

    @PutMapping("/profile")
    public ResponseEntity<PatientResponseDto> updateProfile(
            @RequestHeader("X-Patient-Id") Long patientId,
            @RequestBody PatientRequestDto request) {
        return ResponseEntity.ok(patientService.updateProfile(patientId, request));
    }

    // === APPOINTMENT ENDPOINTS ===
    @GetMapping("/appointments")
    public ResponseEntity<List<Object>> getAppointments(@RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getAppointments(patientId));
    }

    @PostMapping("/appointments")
    public ResponseEntity<String> bookAppointment(
            @RequestHeader("X-Patient-Id") Long patientId,
            @RequestBody Object request) {
        return ResponseEntity.ok(patientService.bookAppointment(patientId, request));
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<String> getAppointmentDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Appointment details for ID: " + id);
    }

    @PutMapping("/appointments/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.cancelAppointment(id));
    }

    // === PRESCRIPTION ENDPOINTS ===
    @GetMapping("/prescriptions")
    public ResponseEntity<List<Object>> getPrescriptions(@RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getPrescriptions(patientId));
    }

    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<String> getPrescriptionDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Prescription details for ID: " + id);
    }

    // === DISEASE ENDPOINTS ===
    @GetMapping("/diseases")
    public ResponseEntity<List<Object>> getDiseases() {
        return ResponseEntity.ok(patientService.getDiseases());
    }

    @GetMapping("/diseases/{id}")
    public ResponseEntity<String> getDiseaseDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Disease details for ID: " + id);
    }

    // === AI DIAGNOSIS ENDPOINT ===
    @PostMapping("/diagnose")
    public ResponseEntity<String> runDiagnose(@RequestBody Object request) {
        return ResponseEntity.ok(patientService.runDiagnose(request));
    }

    // === SYMPTOM REPORT ENDPOINTS ===
    @GetMapping("/symptom-reports")
    public ResponseEntity<List<Object>> getSymptomReports(@RequestHeader("X-Patient-Id") Long patientId) {
        return ResponseEntity.ok(patientService.getSymptomReports(patientId));
    }

    @PostMapping("/symptom-reports")
    public ResponseEntity<String> createSymptomReport(@RequestBody Object request) {
        return ResponseEntity.ok(patientService.createSymptomReport(request));
    }

    @GetMapping("/symptom-reports/{id}")
    public ResponseEntity<String> getSymptomReportDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Symptom report details for ID: " + id);
    }

    @PutMapping("/symptom-reports/{id}")
    public ResponseEntity<String> updateSymptomReport(@PathVariable Long id, @RequestBody Object request) {
        return ResponseEntity.ok(patientService.updateSymptomReport(id, request));
    }

    @DeleteMapping("/symptom-reports/{id}")
    public ResponseEntity<String> deleteSymptomReport(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.deleteSymptomReport(id));
    }
}