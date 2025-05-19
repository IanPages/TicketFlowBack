package com.back.ticketflow.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.List;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "La fecha no puede ser nula")
    private Date bookingDate;

    @NotNull(message = "El estado no puede ser nulo")
    private String status;
    @NotNull(message = "La cantidad no puede ser nula")
    private Integer quantity;
    @NotNull(message = "El precio total no puede ser nulo")
    private Double totalPrice;
    private Boolean vip;
    private String seatIdsTemp;

    @ManyToOne
    private User user;
    @ManyToOne
    private Event event;

    @OneToMany(mappedBy="booking", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Seat> bookedSeats;



    public Booking() {
    }

    public Booking(Integer id, List<Seat> bookedSeats, Event event, User user, String seatIdsTemp, Boolean vip, Double totalPrice, Date bookingDate, String status, Integer quantity) {
        this.id = id;
        this.bookedSeats = bookedSeats;
        this.event = event;
        this.user = user;
        this.seatIdsTemp = seatIdsTemp;
        this.vip = vip;
        this.totalPrice = totalPrice;
        this.bookingDate = bookingDate;
        this.status = status;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Date getBookingDate() {
        return bookingDate;
    }
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public Event getEvent() {
        return event;
    }
    public void setEvent(Event event) {
        this.event = event;
    }
    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }
    public void setBookedSeats(List<Seat> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }
    public Boolean getVip() {
        return vip;
    }
    public void setVip(Boolean vip) {
        this.vip = vip;
    }
    public String getSeatIdsTemp() {
        return seatIdsTemp;
    }
    public void setSeatIdsTemp(String seatIdsTemp) {
        this.seatIdsTemp = seatIdsTemp;
    }
}
