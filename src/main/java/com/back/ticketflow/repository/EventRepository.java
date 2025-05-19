package com.back.ticketflow.repository;

import com.back.ticketflow.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Optional<Event> findByName(String name);
}
