package com.example.Optomeapi.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String specialty;

    @Column(unique = true)
    private String licenseNumber;
    private boolean isActive = true;
    @OneToMany(cascade = CascadeTyepe.ALL, orphanRemoval = true )
    @JoinColumn(name = "doctor_id")
    private List<Experience> experiences = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private  Schedule  schedule;
    @OneToMany(mappedBy = "doctor");
    private List<Appointment> appointments = new ArrayList<>();
    @OneToMany(mappedBy = "doctor")
    private List<Prescription> prescriptions = new ArrayList<>();
    
}
