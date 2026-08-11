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
@Table(name = "signs")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}