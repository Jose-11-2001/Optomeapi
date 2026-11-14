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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    private Long currentPatientId(Authentication auth) {
        return patientService.findIdByUserEmail(auth.getName());
    }

    // ---------- profile ----------
    @GetMapping("/profile")
    public ResponseEntity<PatientResponse> getProfile(Authentication auth) {
        return ResponseEntity.ok(patientService.getProfile(currentPatientId(auth)));
    }

    @PutMapping("/profile")
    public ResponseEntity<PatientResponse> updateProfile(
            Authentication auth,
            @RequestBody PatientRequest request) {
        return ResponseEntity.ok(
                patientService.updateProfile(currentPatientId(auth), request));
    }

    // ---------- appointments ----------
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointments(Authentication auth) {
        return ResponseEntity.ok(patientService.getAppointments(currentPatientId(auth)));
    }

    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponse> bookAppointment(
            Authentication auth,
            @RequestBody BookAppointmentRequest request) {
        return ResponseEntity.ok(
                patientService.bookAppointment(currentPatientId(auth), request));
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

    // ---------- prescriptions ----------
    @GetMapping("/prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getPrescriptions(Authentication auth) {
        return ResponseEntity.ok(patientService.getPrescriptions(currentPatientId(auth)));
    }

    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<String> getPrescriptionDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Prescription details for ID: " + id);
    }

    // ---------- diseases ----------
    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseResponse>> getDiseases() {
        return ResponseEntity.ok(patientService.getDiseases());
    }

    @GetMapping("/diseases/{id}")
    public ResponseEntity<String> getDiseaseDetails(@PathVariable Long id) {
        return ResponseEntity.ok("Disease details for ID: " + id);
    }

    // ---------- diagnose ----------
    @PostMapping("/diagnose")
    public ResponseEntity<DiagnosisResponse> runDiagnose(@RequestBody DiagnoseRequest request) {
        return ResponseEntity.ok(patientService.runDiagnose(request));
    }

    // ---------- symptom reports ----------
    @GetMapping("/symptom-reports")
    public ResponseEntity<List<SymptomReportResponse>> getSymptomReports(Authentication auth) {
        return ResponseEntity.ok(patientService.getSymptomReports(currentPatientId(auth)));
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