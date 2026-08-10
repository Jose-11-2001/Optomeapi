package com.example.Optomeapi.dto.request;

import com.example.Optomeapi.entity.Medication;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PrescriptionRequest {
    @NotNull
    private Long patientId;
    
    private List<Medication> medications;
    
    private String diagnosis;
    
    private String notes;
}
