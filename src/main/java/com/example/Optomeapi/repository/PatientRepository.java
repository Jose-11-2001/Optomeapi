package com.example.Optomeapi.repository;

import com.example.Optomeapi.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    /** Find the patient row for a given user_id (matches User entity's id). */
    Optional<Patient> findByUserId(Long userId);

    /** If you still want email-based lookups anywhere else. */
    Optional<Patient> findByUser_Email(String email);
}