package com.example.Optomeapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Optomeapi.entity.Sign;

import java.util.List;
import java.util.Optional;

@Repository
public interface SignRepository extends JpaRepository<Sign, Long> {
    Optional<Sign> findByName(String name);
    List<Sign> findByCategory(String category);
    boolean existsByName(String name);
}
