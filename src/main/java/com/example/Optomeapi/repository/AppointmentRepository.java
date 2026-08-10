package com.example.Optomeapi.repository;

import com.example.Optomeapi.entity.Appointment;
import com.example.Optomeapi.enum.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByPatientIdAndStatus(Long patientId, AppointmentStatus status);
    List<Appointment> findByDoctorIdAndDate(Long doctorId, LocalDate date);
    List<Appointment> findByDateBetween(LocalDate start, LocalDate end);
    Optional<Appointment> findByIdAndPatientId(Long id, Long patientId);
    Optional<Appointment> findByIdAndDoctorId(Long id, Long doctorId);
    List<Appointment> findByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);
}
