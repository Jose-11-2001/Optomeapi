package com.example.Optomeapi.doctor.dto.request;

import com.example.Optomeapi.entity.Medication;
import lombok.Data;
import java.util.List;

@Data
public class PrescriptionRequest {
    private Long patientId;
    private List<Medication> medications;
    private String diagnosis;
    private String notes;
}
