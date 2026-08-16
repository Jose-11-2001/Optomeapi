package com.example.Optomeapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "prescriptions")
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;  // ✅ ADD THIS FIELD

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id")
    private List<Medication> medications = new ArrayList<>();

    private String diagnosis;
    private String notes;
    private boolean isActive = true;

    public Prescription() {}

    // Getters
    public Long getId() { return id; }
    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public LocalDate getDate() { return date; }
    public List<Medication> getMedications() { return medications; }
    public String getDiagnosis() { return diagnosis; }
    public String getNotes() { return notes; }
    public boolean isActive() { return isActive; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }
    public void setDate(LocalDate date) { this.date = date; }
    public void setMedications(List<Medication> medications) { this.medications = medications; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setActive(boolean active) { isActive = active; }
}