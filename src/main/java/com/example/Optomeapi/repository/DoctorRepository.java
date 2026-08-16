package com.example.Optomeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Optomeapi.entity.Doctor;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
    Optional<Doctor> findByLicenseNumber(String licenseNumber);
    List<Doctor> findBySpecialty(String specialty);
    List<Doctor> findByIsActive(boolean isActive);
    Optional<Doctor> findByIdAndIsActive(Long id, boolean isActive);
}

