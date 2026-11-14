package com.example.Optomeapi.security;

import com.example.Optomeapi.entity.Doctor;
import com.example.Optomeapi.entity.Patient;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.DoctorRepository;
import com.example.Optomeapi.repository.PatientRepository;
import com.example.Optomeapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository; // NEW

    public String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ApiException("User not authenticated", HttpStatus.UNAUTHORIZED);
        }
        return authentication.getName();
    }

    public Long getCurrentUserId() {
        String email = getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
        return user.getId();
    }

    public Long getCurrentDoctorId() {
        Long userId = getCurrentUserId();
        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        return doctor.getId();
    }

    // NEW — same pattern as getCurrentDoctorId()
    public Long getCurrentPatientId() {
        Long userId = getCurrentUserId();
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> new ApiException("Patient profile not found", HttpStatus.NOT_FOUND));
        return patient.getId();
    }

    public User getCurrentUser() {
        String email = getCurrentUserEmail();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found", HttpStatus.NOT_FOUND));
    }

    public boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role));
    }

    public boolean isCurrentUser(Long userId) {
        return getCurrentUserId().equals(userId);
    }
}