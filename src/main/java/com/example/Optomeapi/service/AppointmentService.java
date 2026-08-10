package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.response.AppointmentResponse;
import com.example.Optomeapi.entity.Appointment;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAppointmentService {

    private final AppointmentRepository appointmentRepository;

    public List<AppointmentResponse> getDoctorAppointments(Long doctorId) {
        return appointmentRepository.findByDoctorId(doctorId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public AppointmentResponse getAppointmentDetails(Long doctorId, Long appointmentId) {
        Appointment appointment = appointmentRepository.findByIdAndDoctorId(appointmentId, doctorId)
            .orElseThrow(() -> new ApiException("Appointment not found", HttpStatus.NOT_FOUND));
        return mapToResponse(appointment);
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        return AppointmentResponse.builder()
            .id(appointment.getId())
            .patientId(appointment.getPatient().getId())
            .patientName(appointment.getPatient().getUser().getFirstName() + " " + 
                        appointment.getPatient().getUser().getLastName())
            .doctorId(appointment.getDoctor().getId())
            .doctorName(appointment.getDoctor().getUser().getFirstName() + " " + 
                        appointment.getDoctor().getUser().getLastName())
            .date(appointment.getDate())
            .time(appointment.getTime())
            .status(appointment.getStatus())
            .reason(appointment.getReason())
            .notes(appointment.getNotes())
            .createdAt(appointment.getCreatedAt())
            .build();
    }
}