package com.example.Optomeapi.dto.response;
import com.example.Optomeapi.enum.Role;
import lombok.Builder;  
import lombok.Data;
import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class PrescriptionResponse {
    private Long id;
    private Long doctorId;
    private String doctorName;
    private LocalDate date;
    private List<String> medications;
    private String notes;
    private boolean isActive;
}