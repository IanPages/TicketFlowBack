package com.back.ticketflow.repository;

import com.back.ticketflow.model.GenreEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreEventRepository extends JpaRepository<GenreEvent,Integer> {
    Optional<GenreEvent> findByName(String name);
}
