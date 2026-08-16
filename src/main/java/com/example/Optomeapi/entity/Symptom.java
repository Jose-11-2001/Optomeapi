package com.example.Optomeapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "symptoms")
@Getter
@Setter
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private String type;

    @ElementCollection
    private List<String> commonCauses = new ArrayList<>();

    @ManyToMany(mappedBy = "symptoms")
    private List<Disease> diseases = new ArrayList<>();

    public Symptom() {}
}
