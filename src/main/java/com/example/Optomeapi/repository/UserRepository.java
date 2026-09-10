package com.example.Optomeapi.repository;

import com.example.Optomeapi.entity.User;
import com.example.Optomeapi.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findByIsActive(boolean isActive);
    Optional<User> findByIdAndIsActive(Long id, boolean isActive);
}