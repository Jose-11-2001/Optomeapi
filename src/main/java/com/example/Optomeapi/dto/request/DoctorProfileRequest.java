package com.example.Optomeapi.dto.request;

import lombok.Data;

@Data
public class DoctorProfileRequest {
    private String specialty;
    private String licenseNumber;
}
