package com.example.Optomeapi.service;

import com.example.Optomeapi.dto.response.AppointmentResponse;
import com.example.Optomeapi.entity.Appointment;
import com.example.Optomeapi.enums.AppointmentStatus;
import com.example.Optomeapi.entity.Doctor;
import com.example.Optomeapi.exception.ApiException;
import com.example.Optomeapi.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentBaseService {

    private final AppointmentRepository appointmentRepository;

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
            .orElseThrow(() -> new ApiException("Appointment not found", HttpStatus.NOT_FOUND));
    }

    public boolean isTimeSlotAvailable(Long doctorId, LocalDate date, LocalTime time) {
        List<Appointment> existing = appointmentRepository.findByDoctorIdAndDate(doctorId, date);
        return existing.stream()
            .noneMatch(a -> a.getTime().equals(time) &&
                           a.getStatus() != AppointmentStatus.CANCELLED);
    }

    public AppointmentResponse mapToResponse(Appointment appointment) {
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
