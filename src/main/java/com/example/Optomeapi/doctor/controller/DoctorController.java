package com.example.Optomeapi.controller;
import com.example.Optomeapi.doctor.service.DoctorAppointmentService;
import com.example.Optomeapi.doctor.service.DiseaseService;
import com.example.Optomeapi.doctor.dto.request.*;
import com.example.Optomeapi.doctor.dto.response.DoctorProfileResponse;
import com.example.Optomeapi.dto.response.AppointmentResponse;
import com.example.Optomeapi.dto.response.DiseaseResponse;
import com.example.Optomeapi.dto.response.SymptomResponse;
import com.example.Optomeapi.dto.response.SignResponse;
import com.example.Optomeapi.dto.response.PrescriptionResponse;
import com.example.Optomeapi.security.SecurityUtils;
import com.example.Optomeapi.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final com.example.Optomeapi.doctor.service.DoctorService doctorService;
    private final DoctorAppointmentService appointmentService;
    private final DiseaseService diseaseService;
    private final SymptomService symptomService;
    private final SignService signService;
    private final PrescriptionService prescriptionService;
    private final SecurityUtils securityUtils;

    // ============ PROFILE ENDPOINTS ============
    
    @GetMapping("/profile")
    public ResponseEntity<DoctorProfileResponse> getProfile() {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(doctorService.getDoctorProfile(userId));
    }

    @PutMapping("/profile")
    public ResponseEntity<DoctorProfileResponse> updateProfile(
            @Valid @RequestBody DoctorProfileRequest request) {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(doctorService.updateDoctorProfile(userId, request));
    }

    @PostMapping("/profile/qualifications")
    public ResponseEntity<DoctorProfileResponse> addQualification(
            @Valid @RequestBody QualificationRequest request) {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(doctorService.addQualification(userId, request));
    }

    @PostMapping("/profile/experience")
    public ResponseEntity<DoctorProfileResponse> addExperience(
            @Valid @RequestBody ExperienceRequest request) {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(doctorService.addExperience(userId, request));
    }

    @PutMapping("/profile/schedule")
    public ResponseEntity<DoctorProfileResponse> updateSchedule(
            @Valid @RequestBody ScheduleRequest request) {
        Long userId = securityUtils.getCurrentUserId();
        return ResponseEntity.ok(doctorService.updateSchedule(userId, request));
    }

    // ============ APPOINTMENT ENDPOINTS ============
    
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAppointments() {
        Long doctorId = securityUtils.getCurrentDoctorId();
        return ResponseEntity.ok(appointmentService.getDoctorAppointments(doctorId));
    }

    @GetMapping("/appointments/{id}")
    public ResponseEntity<AppointmentResponse> getAppointmentDetails(@PathVariable Long id) {
        Long doctorId = securityUtils.getCurrentDoctorId();
        return ResponseEntity.ok(appointmentService.getAppointmentDetails(doctorId, id));
    }

    // ============ DISEASE MANAGEMENT ============
    
    @GetMapping("/diseases")
    public ResponseEntity<List<DiseaseResponse>> getAllDiseases() {
        return ResponseEntity.ok(diseaseService.getAllDiseases());
    }

    @PostMapping("/diseases")
    public ResponseEntity<DiseaseResponse> createDisease(
            @Valid @RequestBody DiseaseRequest request) {
        return ResponseEntity.ok(diseaseService.createDisease(request));
    }

    @GetMapping("/diseases/{id}")
    public ResponseEntity<DiseaseResponse> getDiseaseDetails(@PathVariable Long id) {
        return ResponseEntity.ok(diseaseService.getDiseaseById(id));
    }

    @PutMapping("/diseases/{id}")
    public ResponseEntity<DiseaseResponse> updateDisease(
            @PathVariable Long id,
            @Valid @RequestBody DiseaseRequest request) {
        return ResponseEntity.ok(diseaseService.updateDisease(id, request));
    }

    @DeleteMapping("/diseases/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {
        diseaseService.deleteDisease(id);
        return ResponseEntity.noContent().build();
    }

    // ============ SYMPTOM MANAGEMENT ============
    
    @GetMapping("/symptoms")
    public ResponseEntity<List<SymptomResponse>> getAllSymptoms() {
        return ResponseEntity.ok(symptomService.getAllSymptoms());
    }

    @PostMapping("/symptoms")
    public ResponseEntity<SymptomResponse> createSymptom(
            @Valid @RequestBody SymptomRequest request) {
        return ResponseEntity.ok(symptomService.createSymptom(request));
    }

    @GetMapping("/symptoms/{id}")
    public ResponseEntity<SymptomResponse> getSymptomDetails(@PathVariable Long id) {
        return ResponseEntity.ok(symptomService.getSymptomById(id));
    }

    @PutMapping("/symptoms/{id}")
    public ResponseEntity<SymptomResponse> updateSymptom(
            @PathVariable Long id,
            @Valid @RequestBody SymptomRequest request) {
        return ResponseEntity.ok(symptomService.updateSymptom(id, request));
    }

    @DeleteMapping("/symptoms/{id}")
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        symptomService.deleteSymptom(id);
        return ResponseEntity.noContent().build();
    }

    // ============ SIGN MANAGEMENT ============
    
    @GetMapping("/signs")
    public ResponseEntity<List<SignResponse>> getAllSigns() {
        return ResponseEntity.ok(signService.getAllSigns());
    }

    @PostMapping("/signs")
    public ResponseEntity<SignResponse> createSign(
            @Valid @RequestBody SignRequest request) {
        return ResponseEntity.ok(signService.createSign(request));
    }

    @GetMapping("/signs/{id}")
    public ResponseEntity<SignResponse> getSignDetails(@PathVariable Long id) {
        return ResponseEntity.ok(signService.getSignById(id));
    }

    @PutMapping("/signs/{id}")
    public ResponseEntity<SignResponse> updateSign(
            @PathVariable Long id,
            @Valid @RequestBody SignRequest request) {
        return ResponseEntity.ok(signService.updateSign(id, request));
    }

    @DeleteMapping("/signs/{id}")
    public ResponseEntity<Void> deleteSign(@PathVariable Long id) {
        signService.deleteSign(id);
        return ResponseEntity.noContent().build();
    }

    // ============ PRESCRIPTION MANAGEMENT ============
    
    @GetMapping("/prescriptions")
    public ResponseEntity<List<PrescriptionResponse>> getAllPrescriptions() {
        Long doctorId = securityUtils.getCurrentDoctorId();
        return ResponseEntity.ok(prescriptionService.getDoctorPrescriptions(doctorId));
    }

    @PostMapping("/prescriptions")
    public ResponseEntity<PrescriptionResponse> createPrescription(
            @Valid @RequestBody PrescriptionRequest request) {
        Long doctorId = securityUtils.getCurrentDoctorId();
        return ResponseEntity.ok(prescriptionService.createPrescription(doctorId, request));
    }

    @GetMapping("/prescriptions/{id}")
    public ResponseEntity<PrescriptionResponse> getPrescriptionDetails(@PathVariable Long id) {
        Long doctorId = securityUtils.getCurrentDoctorId();
        return ResponseEntity.ok(prescriptionService.getPrescriptionDetails(doctorId, id));
    }

    @PutMapping("/prescriptions/{id}")
    public ResponseEntity<PrescriptionResponse> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionRequest request) {
        Long doctorId = securityUtils.getCurrentDoctorId();
        return ResponseEntity.ok(prescriptionService.updatePrescription(doctorId, id, request));
    }

    @DeleteMapping("/prescriptions/{id}")
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        Long doctorId = securityUtils.getCurrentDoctorId();
        prescriptionService.deletePrescription(doctorId, id);
        return ResponseEntity.noContent().build();
    }
}