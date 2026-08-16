package com.example.Optomeapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "experiences")
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;
    private String hospital;
    private String startDate;
    private String endDate; // null means current

    // === CONSTRUCTORS ===
    public Experience() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public String getPosition() { return position; }
    public String getHospital() { return hospital; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setPosition(String position) { this.position = position; }
    public void setHospital(String hospital) { this.hospital = hospital; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
}
