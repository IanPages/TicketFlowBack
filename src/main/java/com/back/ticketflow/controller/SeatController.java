package com.back.ticketflow.controller;

import com.back.ticketflow.model.Seat;
import com.back.ticketflow.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    private SeatRepository seatRepository;

    @Autowired
    public SeatController(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @GetMapping("/event/{eventId}")
    public List<Seat> getSeatsByEvent(@PathVariable Integer eventId) {
        return seatRepository.findByEventId(eventId);
    }
}
