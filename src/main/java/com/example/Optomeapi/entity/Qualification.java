package com.example.Optomeapi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "qualifications")
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String degree;
    private String institution;
    private Integer year;

    // === CONSTRUCTORS ===
    public Qualification() {}

    // === GETTERS ===
    public Long getId() { return id; }
    public String getDegree() { return degree; }
    public String getInstitution() { return institution; }
    public Integer getYear() { return year; }

    // === SETTERS ===
    public void setId(Long id) { this.id = id; }
    public void setDegree(String degree) { this.degree = degree; }
    public void setInstitution(String institution) { this.institution = institution; }
    public void setYear(Integer year) { this.year = year; }
}
