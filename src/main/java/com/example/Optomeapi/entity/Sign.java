package com.example.Optomeapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "signs")
@Getter
@Setter
public class Sign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;
    private String category;
    private String severityIndicator;

    @ManyToMany(mappedBy = "signs")
    private List<Disease> diseases = new ArrayList<>();

    public Sign() {}
}
