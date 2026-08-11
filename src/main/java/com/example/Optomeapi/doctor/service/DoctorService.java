package com.example.Optomeapi.doctor.service;

import com.example.Optomeapi.doctor.dto.request.DoctorProfileRequest;
import com.example.Optomeapi.doctor.dto.request.ExperienceRequest;
import com.example.Optomeapi.doctor.dto.request.QualificationRequest;
import com.example.Optomeapi.doctor.dto.request.ScheduleRequest;
import com.example.Optomeapi.doctor.dto.response.DoctorProfileResponse;
import com.example.Optomeapi.entity.Doctor;
import com.example.Optomeapi.entity.Experience;
import com.example.Optomeapi.entity.Qualification;
import com.example.Optomeapi.entity.Schedule;
import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.DoctorRepository;
import com.example.Optomeapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserService userService;

    @Transactional
    public Doctor createDoctorProfile(User user) {
        Doctor doctor = Doctor.builder()
            .user(user)
            .isActive(true)
            .build();
        return doctorRepository.save(doctor);
    }

    public DoctorProfileResponse getDoctorProfile(Long userId) {
        Doctor doctor = getDoctorByUserId(userId);
        return mapToProfileResponse(doctor);
    }

    @Transactional
    public DoctorProfileResponse updateDoctorProfile(Long userId, DoctorProfileRequest request) {
        Doctor doctor = getDoctorByUserId(userId);
        
        if (request.getSpecialty() != null) {
            doctor.setSpecialty(request.getSpecialty());
        }
        if (request.getLicenseNumber() != null) {
            if (doctorRepository.findByLicenseNumber(request.getLicenseNumber()).isPresent() &&
                !doctor.getLicenseNumber().equals(request.getLicenseNumber())) {
                throw new ApiException("License number already exists", HttpStatus.CONFLICT);
            }
            doctor.setLicenseNumber(request.getLicenseNumber());
        }
        
        Doctor updated = doctorRepository.save(doctor);
        return mapToProfileResponse(updated);
    }

    @Transactional
    public DoctorProfileResponse addQualification(Long userId, QualificationRequest request) {
        Doctor doctor = getDoctorByUserId(userId);
        
        Qualification qualification = Qualification.builder()
            .degree(request.getDegree())
            .institution(request.getInstitution())
            .year(request.getYear())
            .build();
        
        doctor.getQualifications().add(qualification);
        Doctor updated = doctorRepository.save(doctor);
        return mapToProfileResponse(updated);
    }

    @Transactional
    public DoctorProfileResponse addExperience(Long userId, ExperienceRequest request) {
        Doctor doctor = getDoctorByUserId(userId);
        
        Experience experience = Experience.builder()
            .position(request.getPosition())
            .hospital(request.getHospital())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .build();
        
        doctor.getExperiences().add(experience);
        Doctor updated = doctorRepository.save(doctor);
        return mapToProfileResponse(updated);
    }

    @Transactional
    public DoctorProfileResponse updateSchedule(Long userId, ScheduleRequest request) {
        Doctor doctor = getDoctorByUserId(userId);
        
        Schedule schedule = doctor.getSchedule();
        if (schedule == null) {
            schedule = new Schedule();
        }
        
        if (request.getMonday() != null) schedule.setMonday(request.getMonday());
        if (request.getTuesday() != null) schedule.setTuesday(request.getTuesday());
        if (request.getWednesday() != null) schedule.setWednesday(request.getWednesday());
        if (request.getThursday() != null) schedule.setThursday(request.getThursday());
        if (request.getFriday() != null) schedule.setFriday(request.getFriday());
        if (request.getSaturday() != null) schedule.setSaturday(request.getSaturday());
        if (request.getSunday() != null) schedule.setSunday(request.getSunday());
        
        doctor.setSchedule(schedule);
        Doctor updated = doctorRepository.save(doctor);
        return mapToProfileResponse(updated);
    }

    public Doctor getDoctorByUserId(Long userId) {
        return doctorRepository.findByUserId(userId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
    }

    public Doctor getDoctorEntity(Long doctorId) {
        return doctorRepository.findById(doctorId)
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
    }

    private DoctorProfileResponse mapToProfileResponse(Doctor doctor) {
        return DoctorProfileResponse.builder()
            .id(doctor.getId())
            .user(userService.mapToUserResponse(doctor.getUser()))
            .specialty(doctor.getSpecialty())
            .licenseNumber(doctor.getLicenseNumber())
            .isActive(doctor.isActive())
            .qualifications(doctor.getQualifications())
            .experiences(doctor.getExperiences())
            .schedule(doctor.getSchedule())
            .build();
    }
}
