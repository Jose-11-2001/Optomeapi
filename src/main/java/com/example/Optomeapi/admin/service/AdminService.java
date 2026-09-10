package com.example.Optomeapi.admin.service;

import com.example.Optomeapi.admin.dto.request.ChangePasswordRequest;
import com.example.Optomeapi.admin.dto.request.CreateDoctorRequest;
import com.example.Optomeapi.admin.dto.response.DoctorManagementResponse;
import com.example.Optomeapi.admin.dto.response.ResetPasswordResponse;
import com.example.Optomeapi.entity.Doctor;
import com.example.Optomeapi.enums.Role;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.DoctorRepository;
import com.example.Optomeapi.repository.UserRepository;
import com.example.Optomeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public List<DoctorManagementResponse> getAllDoctors() {
        List<User> doctors = userRepository.findByRole(Role.DOCTOR);
        return doctors.stream()
            .map(user -> {
                Doctor doctor = doctorRepository.findByUserId(user.getId()).orElse(null);
                return mapToDoctorResponse(user, doctor);
            })
            .collect(Collectors.toList());
    }

    @Transactional
    public DoctorManagementResponse createDoctor(CreateDoctorRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException("Email already registered", HttpStatus.CONFLICT);
        }

        if (doctorRepository.findByLicenseNumber(request.getLicenseNumber()).isPresent()) {
            throw new ApiException("License number already exists", HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setRole(Role.DOCTOR);
        user.setActive(true);

        User savedUser = userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(savedUser);
        doctor.setSpecialty(request.getSpecialty());
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setActive(true);

        Doctor savedDoctor = doctorRepository.save(doctor);
        return mapToDoctorResponse(savedUser, savedDoctor);
    }

    @Transactional
    public DoctorManagementResponse updateDoctor(Long doctorId, CreateDoctorRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        
        User user = doctor.getUser();
        
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new ApiException("Email already exists", HttpStatus.CONFLICT);
            }
            user.setEmail(request.getEmail());
        }
        
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        
        if (request.getSpecialty() != null) doctor.setSpecialty(request.getSpecialty());
        if (request.getLicenseNumber() != null && !request.getLicenseNumber().equals(doctor.getLicenseNumber())) {
            if (doctorRepository.findByLicenseNumber(request.getLicenseNumber()).isPresent()) {
                throw new ApiException("License number already exists", HttpStatus.CONFLICT);
            }
            doctor.setLicenseNumber(request.getLicenseNumber());
        }
        
        User updatedUser = userRepository.save(user);
        Doctor updatedDoctor = doctorRepository.save(doctor);
        return mapToDoctorResponse(updatedUser, updatedDoctor);
    }

    @Transactional
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        
        User user = doctor.getUser();
        user.setActive(false);
        userRepository.save(user);
    }

    @Transactional
    public void changeDoctorPassword(Long doctorId, ChangePasswordRequest request) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        
        User user = doctor.getUser();
        
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new ApiException("Invalid old password", HttpStatus.BAD_REQUEST);
        }
        
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public ResetPasswordResponse resetDoctorPassword(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        
        User user = doctor.getUser();
        String newPassword = "Temp" + System.currentTimeMillis() + "!";
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        ResetPasswordResponse response = new ResetPasswordResponse();
        response.setNewPassword(newPassword);
        response.setMessage("Password has been reset successfully");
        return response;
    }

    @Transactional
    public void activateDoctor(Long doctorId, boolean activate) {
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        
        User user = doctor.getUser();
        user.setActive(activate);
        userRepository.save(user);
    }

    private DoctorManagementResponse mapToDoctorResponse(User user, Doctor doctor) {
        DoctorManagementResponse response = new DoctorManagementResponse();
        response.setId(doctor.getId());
        response.setUser(userService.mapToUserResponse(user));
        response.setSpecialty(doctor.getSpecialty());
        response.setLicenseNumber(doctor.getLicenseNumber());
        response.setActive(doctor.isActive());
        return response;
    }
}