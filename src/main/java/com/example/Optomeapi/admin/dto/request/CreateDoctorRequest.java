package com.example.Optomeapi.admin.dto.request;

import com.example.Optomeapi.enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDoctorRequest {
    @NotBlank @Email
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    private String phone;
    
    @NotBlank
    private String specialty;
    
    @NotBlank
    private String licenseNumber;
}

