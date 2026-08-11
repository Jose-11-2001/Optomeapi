package com.example.Optomeapi.doctor.dto.response;

import com.example.Optomeapi.dto.response.UserResponse;
import com.example.Optomeapi.entity.Experience;
import com.example.Optomeapi.entity.Qualification;
import com.example.Optomeapi.entity.Schedule;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
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
