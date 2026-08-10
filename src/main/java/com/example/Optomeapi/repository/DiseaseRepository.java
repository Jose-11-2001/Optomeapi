package com.example.Optomeapi.repository;

import com.example.Optomeapi.entity.Disease;
import com.example.Optomeapi.enum.Severity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    Optional<Disease> findByName(String name);
    List<Disease> findBySeverity(Severity severity);
    boolean existsByName(String name);
}