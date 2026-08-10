package com.example.Optomeapi.admin.dto.response;

import com.example.Optomeapi.dto.response.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorManagementResponse {
    private Long id;
    private UserResponse user;
    private String specialty;
    private String licenseNumber;
    private boolean isActive;
}