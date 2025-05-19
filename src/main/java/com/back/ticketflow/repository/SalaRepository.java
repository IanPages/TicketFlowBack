package com.back.ticketflow.repository;

import com.back.ticketflow.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SalaRepository extends JpaRepository<Sala, Integer> {
    Optional<Sala> findByName(String name);
}
