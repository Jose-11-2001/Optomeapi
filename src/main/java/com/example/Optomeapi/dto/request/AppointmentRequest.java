package com.example.Optomeapi.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentRequest {
    @NotNull
    private Long doctorId;
    
    @NotNull @Future
    private LocalDate date;
    
    @NotNull
    private LocalTime time;
    
    @NotBlank
    private String reason;
    
    private String notes;
}