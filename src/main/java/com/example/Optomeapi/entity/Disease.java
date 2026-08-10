package com.example.Optomeapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "diseases")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 5000)
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
}