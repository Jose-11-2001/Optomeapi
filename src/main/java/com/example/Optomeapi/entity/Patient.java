package com.example.Optomeapi.entity;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions = new ArrayList<>();

    // === CONSTRUCTORS ===
    public Patient() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public User getUser() { return user; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Prescription> getPrescriptions() { return prescriptions; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }
}
