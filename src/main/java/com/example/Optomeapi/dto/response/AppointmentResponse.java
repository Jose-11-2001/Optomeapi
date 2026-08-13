package com.example.Optomeapi.dto.response;

import com.example.Optomeapi.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private Long id;
    private Long patientId;
    private String patientName;
    private Long doctorId;
    private String doctorName;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus status;
    private String reason;
    private String notes;
    private LocalDateTime createdAt;
}
