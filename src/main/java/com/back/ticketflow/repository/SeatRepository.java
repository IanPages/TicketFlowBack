package com.back.ticketflow.repository;


import com.back.ticketflow.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> findByEventId(Integer eventId);
    void deleteByEventId(Integer eventId);
}
