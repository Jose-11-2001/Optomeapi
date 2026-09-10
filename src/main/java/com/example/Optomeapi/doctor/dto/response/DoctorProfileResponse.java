package com.example.Optomeapi.doctor.dto.response;

import com.example.Optomeapi.dto.response.UserResponse;
import com.example.Optomeapi.entity.Experience;
import com.example.Optomeapi.entity.Qualification;
import com.example.Optomeapi.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorProfileResponse {
    private Long id;
    private UserResponse user;
    private String specialty;
    private String licenseNumber;
    private boolean isActive;
    private List<Qualification> qualifications;
    private List<Experience> experiences;
    private Schedule schedule;
}
