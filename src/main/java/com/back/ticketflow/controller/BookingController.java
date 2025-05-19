package com.back.ticketflow.controller;

import com.back.ticketflow.dto.BookingDTO;
import com.back.ticketflow.model.Booking;
import com.back.ticketflow.service.BookingService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> createCheckout(@RequestBody BookingDTO request) throws StripeException {
        String checkoutUrl = bookingService.createBookingAndGetCheckoutUrl(request);
        return ResponseEntity.ok(Map.of("url", checkoutUrl));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Optional<Booking>> getBookingById(@PathVariable Integer bookingId) {
        return ResponseEntity.ok(bookingService.getBookingById(bookingId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Optional<List<Booking>>> getUserBookings(@PathVariable Integer userId) {
        return ResponseEntity.ok(bookingService.getBookingsByUser(userId));
    }
}
