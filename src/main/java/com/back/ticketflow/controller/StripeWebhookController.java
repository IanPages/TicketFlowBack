package com.back.ticketflow.controller;

import com.back.ticketflow.model.Booking;
import com.back.ticketflow.model.Seat;
import com.back.ticketflow.repository.BookingRepository;
import com.back.ticketflow.repository.SeatRepository;
import com.back.ticketflow.service.EmailService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.model.checkout.Session;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/stripe")
public class StripeWebhookController {

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;
    private EmailService emailService;

    @Autowired
    public StripeWebhookController(BookingRepository bookingRepository, SeatRepository seatRepository, EmailService emailService) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
        this.emailService = emailService;
    }

    @Value("${stripe.webhook.secret}")
    private String secretWebhook;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        String endpointSecret = secretWebhook;
        Event stripeEvent;

        try {
            stripeEvent = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (SignatureVerificationException e) {
            return ResponseEntity.badRequest().body("Invalid signature");
        }

        if ("checkout.session.completed".equals(stripeEvent.getType())) {
            com.stripe.model.checkout.Session session = (com.stripe.model.checkout.Session) stripeEvent.getData().getObject();

            // Verificar si session es nulo
            if (session != null && session.getMetadata() != null) {
                String bookingIdStr = session.getMetadata().get("bookingId");

                if (bookingIdStr != null) {
                    try {
                        Integer bookingId = Integer.parseInt(bookingIdStr);
                        Booking booking = bookingRepository.findById(bookingId).orElse(null);

                        if (booking != null) {
                            booking.setStatus("CONFIRMED");

                            if (booking.getEvent().getSala() != null && booking.getSeatIdsTemp() != null) {
                                List<Integer> seatIds = Arrays.stream(booking.getSeatIdsTemp().split(","))
                                        .map(Integer::parseInt).toList();

                                System.out.println("Seat IDs: " + seatIds);

                                List<Seat> seats = seatRepository.findAllById(seatIds);
                                System.out.println("Seats found: " + seats);

                                if (!seats.isEmpty()) {
                                    for (Seat seat : seats) {
                                        seat.setOcupado(true);
                                    }

                                    booking.setBookedSeats(seats);
                                    System.out.println("Booked seats: " + booking.getBookedSeats());

                                    seatRepository.saveAll(seats);
                                    bookingRepository.save(booking);
                                } else {
                                    System.out.println("No seats found for the given IDs.");
                                }
                            }
                            String email = session.getCustomerDetails() != null ? session.getCustomerDetails().getEmail() : null;
                            if (email != null) {
                                emailService.sendBookingConfirmation(email, booking);
                            }
                        }
                    } catch (NumberFormatException e) {
                        return ResponseEntity.badRequest().body("Invalid bookingId format");
                    }
                } else {
                    return ResponseEntity.badRequest().body("Missing bookingId in metadata");
                }
            }
        }

        return ResponseEntity.ok("Webhook handled");
    }


}

