package com.example.Optomeapi.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "symptoms")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
