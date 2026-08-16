package com.example.Optomeapi.admin.service;

import com.example.Optomeapi.admin.dto.request.AllocateAppointmentRequest;
import com.example.Optomeapi.dto.response.AppointmentResponse;
import com.example.Optomeapi.entity.Appointment;
import com.example.Optomeapi.enums.AppointmentStatus;
import com.example.Optomeapi.entity.Doctor;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.AppointmentRepository;
import com.example.Optomeapi.repository.DoctorRepository;
import com.example.Optomeapi.service.AppointmentBaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentBaseService appointmentBaseService;

    public List<AppointmentResponse> getAllAppointments(
            LocalDate fromDate, LocalDate toDate, Long doctorId, AppointmentStatus status) {
        
        List<Appointment> appointments;
        
        if (fromDate != null && toDate != null) {
            appointments = appointmentRepository.findByDateBetween(fromDate, toDate);
        } else if (doctorId != null) {
            appointments = appointmentRepository.findByDoctorId(doctorId);
        } else if (status != null) {
            appointments = appointmentRepository.findByDoctorIdAndStatus(
                doctorId != null ? doctorId : 0, status);
        } else {
            appointments = appointmentRepository.findAll();
        }
        
        // Additional filtering by status if needed
        if (status != null && appointments.isEmpty()) {
            appointments = appointmentRepository.findAll().stream()
                .filter(a -> a.getStatus() == status)
                .collect(Collectors.toList());
        }
        
        return appointments.stream()
            .map(appointmentBaseService::mapToResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentResponse approveAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new ApiException("Appointment not found", HttpStatus.NOT_FOUND));
        
        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new ApiException("Only scheduled appointments can be approved", HttpStatus.BAD_REQUEST);
        }
        
        appointment.setStatus(AppointmentStatus.APPROVED);
        Appointment updated = appointmentRepository.save(appointment);
        return appointmentBaseService.mapToResponse(updated);
    }

    @Transactional
    public AppointmentResponse refuseAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new ApiException("Appointment not found", HttpStatus.NOT_FOUND));
        
        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new ApiException("Only scheduled appointments can be refused", HttpStatus.BAD_REQUEST);
        }
        
        appointment.setStatus(AppointmentStatus.REFUSED);
        Appointment updated = appointmentRepository.save(appointment);
        return appointmentBaseService.mapToResponse(updated);
    }

    @Transactional
    public AppointmentResponse allocateAppointment(Long appointmentId, AllocateAppointmentRequest request) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new ApiException("Appointment not found", HttpStatus.NOT_FOUND));
        
        if (appointment.getStatus() != AppointmentStatus.APPROVED) {
            throw new ApiException("Only approved appointments can be allocated", HttpStatus.BAD_REQUEST);
        }
        
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
            .orElseThrow(() -> new ApiException("Doctor not found", HttpStatus.NOT_FOUND));
        
        // Check if doctor is available at that time
        boolean isAvailable = appointmentBaseService.isTimeSlotAvailable(
            doctor.getId(), appointment.getDate(), appointment.getTime());
        
        if (!isAvailable) {
            throw new ApiException("Doctor is not available at this time", HttpStatus.CONFLICT);
        }
        
        appointment.setDoctor(doctor);
        appointment.setStatus(AppointmentStatus.APPROVED);
        Appointment updated = appointmentRepository.save(appointment);
        return appointmentBaseService.mapToResponse(updated);
    }
}