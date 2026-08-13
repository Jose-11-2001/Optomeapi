package com.example.Optomeapi.entity;

import com.example.Optomeapi.enums.Severity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "symptom_reports")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class SymptomReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;  // ✅ ADD THIS FIELD

    @ElementCollection
    private List<String> symptoms = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Severity severity;

    private String duration;
    private String status;
    private String notes;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public SymptomReport() {}

    // Getters
    public Long getId() { return id; }
    public Patient getPatient() { return patient; }
    public List<String> getSymptoms() { return symptoms; }
    public Severity getSeverity() { return severity; }
    public String getDuration() { return duration; }
    public String getStatus() { return status; }
    public String getNotes() { return notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setSymptoms(List<String> symptoms) { this.symptoms = symptoms; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public void setDuration(String duration) { this.duration = duration; }
    public void setStatus(String status) { this.status = status; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
