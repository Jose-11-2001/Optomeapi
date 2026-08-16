package com.example.Optomeapi.doctor.service;

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
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setPatientId(1L);
        response.setPatientName("Patient Name");
        response.setDoctorId(appointment.getDoctor().getId());
        response.setDoctorName(appointment.getDoctor().getUser().getFirstName() + " " +
                              appointment.getDoctor().getUser().getLastName());
        response.setDate(appointment.getDate());
        response.setTime(appointment.getTime());
        response.setStatus(appointment.getStatus());
        response.setReason(appointment.getReason());
        response.setNotes(appointment.getNotes());
        response.setCreatedAt(appointment.getCreatedAt());
        return response;
    }
}
