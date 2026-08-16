package com.example.Optomeapi.admin.dto.response;

import com.example.Optomeapi.dto.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorManagementResponse {
    private Long id;
    private UserResponse user;
    private String specialty;
    private String licenseNumber;
    private boolean isActive;
}
