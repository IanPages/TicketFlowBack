package com.back.ticketflow.dto;

import java.util.List;

public class BookingDTO {

    private Integer userId;
    private Integer eventId;
    private Integer quantity;
    private Boolean vip;
    private List<Integer> seatIds;

    public BookingDTO() {
    }
    public BookingDTO(Integer userId, Integer eventId, Integer quantity, Boolean vip, List<Integer> seatIds) {
        this.userId = userId;
        this.eventId = eventId;
        this.quantity = quantity;
        this.vip = vip;
        this.seatIds = seatIds;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getEventId() {
        return eventId;
    }
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Boolean getVip() {
        return vip;
    }
    public void setVip(Boolean vip) {
        this.vip = vip;
    }
    public List<Integer> getSeatIds() {
        return seatIds;
    }
    public void setSeatIds(List<Integer> seatIds) {
        this.seatIds = seatIds;
    }
}
