package com.back.ticketflow.service;

import com.back.ticketflow.dto.BookingDTO;
import com.back.ticketflow.model.Booking;
import com.back.ticketflow.model.Event;
import com.back.ticketflow.model.User;
import com.back.ticketflow.repository.BookingRepository;
import com.back.ticketflow.repository.EventRepository;
import com.back.ticketflow.repository.UserRepository;
import com.stripe.exception.StripeException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private UserRepository userRepository;
    private EventRepository eventRepository;
    private BookingRepository bookingRepository;
    private StripeService stripeService;

    @Autowired
    public BookingService(UserRepository userRepository, EventRepository eventRepository, BookingRepository bookingRepository, StripeService stripeService) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.bookingRepository = bookingRepository;
        this.stripeService = stripeService;
    }

    //Create Reserva y redirigir Stripe checkout
    @Transactional
    public String createBookingAndGetCheckoutUrl(BookingDTO request) throws StripeException {
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        Event event = eventRepository.findById(request.getEventId()).orElseThrow();

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setEvent(event);
        booking.setVip(request.getVip());
        booking.setBookingDate(new Date(System.currentTimeMillis()));
        booking.setStatus("PENDING");
        booking.setQuantity(request.getQuantity());

        double price = request.getVip() ? event.getVipPrice() : event.getNormalPrice();
        booking.setTotalPrice(price * request.getQuantity());

        if (event.getSala() != null && request.getSeatIds() != null){
            //guardo en el SeatIdsTemp los ids de los asientos seleccionados
            booking.setSeatIdsTemp(request.getSeatIds().stream().map(String::valueOf).collect(Collectors.joining(",")));
        }

        bookingRepository.save(booking);
        return stripeService.createCheckoutSession(event, request.getQuantity(),request.getVip(),booking.getId());
    }
    public Optional<List<Booking>> getBookingsByUser(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<Booking> getBookingById(Integer bookingId) {
        return bookingRepository.findById(bookingId);
    }
}
