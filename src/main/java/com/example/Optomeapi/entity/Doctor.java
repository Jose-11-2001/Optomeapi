package com.example.Optomeapi.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String specialty;
    private String licenseNumber;
    private boolean isActive = true;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "doctor_id")
    private List<Qualification> qualifications = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "doctor_id")
    private List<Experience> experiences = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<Appointment> appointments = new ArrayList<>();

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    private List<Prescription> prescriptions = new ArrayList<>();

    // === CONSTRUCTORS ===
    public Doctor() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getSpecialty() { return specialty; }
    public String getLicenseNumber() { return licenseNumber; }
    public boolean isActive() { return isActive; }
    public List<Qualification> getQualifications() { return qualifications; }
    public List<Experience> getExperiences() { return experiences; }
    public Schedule getSchedule() { return schedule; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Prescription> getPrescriptions() { return prescriptions; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public void setLicenseNumber(String licenseNumber) { this.licenseNumber = licenseNumber; }
    public void setActive(boolean active) { isActive = active; }
    public void setQualifications(List<Qualification> qualifications) { this.qualifications = qualifications; }
    public void setExperiences(List<Experience> experiences) { this.experiences = experiences; }
    public void setSchedule(Schedule schedule) { this.schedule = schedule; }
    public void setAppointments(List<Appointment> appointments) { this.appointments = appointments; }
    public void setPrescriptions(List<Prescription> prescriptions) { this.prescriptions = prescriptions; }
}
