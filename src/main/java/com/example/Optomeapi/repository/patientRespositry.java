package com.example.Optomeapi.repository;

import com.example.Optomeapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface patientRespositry extends JpaRepository<Patient, Long> {
    Optional<Patient> findByUserId(Long userId);
}