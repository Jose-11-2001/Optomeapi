package com.example.Optomeapi.dto.response;

import com.example.Optomeapi.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionResponse {
    private Long id;
    private Long doctorId;
    private String doctorName;
    private LocalDate date;
    private List<Medication> medications;
    private String diagnosis;
    private String notes;
    private boolean isActive;
}
