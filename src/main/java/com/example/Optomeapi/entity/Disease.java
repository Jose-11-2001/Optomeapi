package com.example.Optomeapi.entity;
import com.example.Optomeapi.enums.Severity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 2000)
    private String description;

    @ManyToMany
    @JoinTable(
        name = "disease_symptoms",
        joinColumns = @JoinColumn(name = "disease_id"),
        inverseJoinColumns = @JoinColumn(name = "symptom_id")
    )
    private List<Symptom> symptoms = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "disease_signs",
        joinColumns = @JoinColumn(name = "disease_id"),
        inverseJoinColumns = @JoinColumn(name = "sign_id")
    )
    private List<Sign> signs = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Severity severity;

    @Column(length = 1000)
    private String treatment;

    @ElementCollection
    private List<String> riskFactors = new ArrayList<>();

    // === CONSTRUCTORS ===
    public Disease() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<Symptom> getSymptoms() { return symptoms; }
    public List<Sign> getSigns() { return signs; }
    public Severity getSeverity() { return severity; }
    public String getTreatment() { return treatment; }
    public List<String> getRiskFactors() { return riskFactors; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setSymptoms(List<Symptom> symptoms) { this.symptoms = symptoms; }
    public void setSigns(List<Sign> signs) { this.signs = signs; }
    public void setSeverity(Severity severity) { this.severity = severity; }
    public void setTreatment(String treatment) { this.treatment = treatment; }
    public void setRiskFactors(List<String> riskFactors) { this.riskFactors = riskFactors; }
}