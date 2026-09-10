package com.example.Optomeapi.admin.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AllocateAppointmentRequest {
    @NotNull
    private Long doctorId;
}