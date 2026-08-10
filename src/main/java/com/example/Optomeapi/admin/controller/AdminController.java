package com.example.Optomeapi.admin.controller;

import com.example.Optomeapi.admin.dto.request.AllocateAppointmentRequest;
import com.example.Optomeapi.admin.dto.request.ChangePasswordRequest;
import com.example.Optomeapi.admin.dto.request.CreateDoctorRequest;
import com.example.Optomeapi.admin.dto.response.DoctorManagementResponse;
import com.example.Optomeapi.admin.dto.response.ResetPasswordResponse;
import com.example.Optomeapi.admin.service.AdminAppointmentService;
import com.example.Optomeapi.admin.service.AdminService;
import com.example.Optomeapi.admin.service.AdminUserService;
import com.example.Optomeapi.dto.response.AppointmentResponse;
import com.example.Optomeapi.dto.response.UserResponse;
import com.example.Optomeapi.enum.AppointmentStatus;
import com.example.Optomeapi.enum.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminAppointmentService adminAppointmentService;
    private final AdminUserService adminUserService;

    // Doctor management
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorManagementResponse>> getAllDoctors() {
        return ResponseEntity.ok(adminService.getAllDoctors());
    }

    @PostMapping("/doctors")
    public ResponseEntity<DoctorManagementResponse> createDoctor(
            @Valid @RequestBody CreateDoctorRequest request) {
        return ResponseEntity.ok(adminService.createDoctor(request));
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<DoctorManagementResponse> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody CreateDoctorRequest request) {
        return ResponseEntity.ok(adminService.updateDoctor(id, request));
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        adminService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/doctors/{id}/change-password")
    public ResponseEntity<Void> changeDoctorPassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request) {
        adminService.changeDoctorPassword(id, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/doctors/{id}/reset-password")
    public ResponseEntity<ResetPasswordResponse> resetDoctorPassword(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.resetDoctorPassword(id));
    }

    @PutMapping("/doctors/{id}/activate")
    public ResponseEntity<Void> activateDoctor(
            @PathVariable Long id,
            @RequestParam boolean active) {
        adminService.activateDoctor(id, active);
        return ResponseEntity.ok().build();
    }

    // Appointment management
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getAllAppointments(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) Long doctorId,
            @RequestParam(required = false) AppointmentStatus status) {
        return ResponseEntity.ok(adminAppointmentService.getAllAppointments(fromDate, toDate, doctorId, status));
    }

    @PutMapping("/appointments/{id}/approve")
    public ResponseEntity<AppointmentResponse> approveAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(adminAppointmentService.approveAppointment(id));
    }

    @PutMapping("/appointments/{id}/refuse")
    public ResponseEntity<AppointmentResponse> refuseAppointment(@PathVariable Long id) {
        return ResponseEntity.ok(adminAppointmentService.refuseAppointment(id));
    }

    @PutMapping("/appointments/{id}/allocate")
    public ResponseEntity<AppointmentResponse> allocateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AllocateAppointmentRequest request) {
        return ResponseEntity.ok(adminAppointmentService.allocateAppointment(id, request));
    }

    // User management
    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean isActive) {
        return ResponseEntity.ok(adminUserService.getAllUsers(role, isActive));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}