package com.back.ticketflow.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;

public class EventDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    @NotNull(message = "La fecha es obligatoria")
    private Date date;

    @NotBlank(message = "La localización es obligatoria")
    private String location;

    private Integer capacity;

    @NotNull(message = "El precio normal es obligatorio")
    private Double normalPrice;
    private Double vipPrice;

    @NotNull(message = "El género es obligatorio")
    private Integer genreId;
    private Integer salaId;

    public EventDTO(){}

    public EventDTO(Double vipPrice, Integer salaId, Integer genreId, Double normalPrice, Integer capacity, String location, Date date, String description, String name) {
        this.vipPrice = vipPrice;
        this.salaId = salaId;
        this.genreId = genreId;
        this.normalPrice = normalPrice;
        this.capacity = capacity;
        this.location = location;
        this.date = date;
        this.description = description;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getCapacity() {
        return capacity;
    }
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    public Integer getGenreId() {
        return genreId;
    }
    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }
    public Integer getSalaId() {
        return salaId;
    }
    public void setSalaId(Integer salaId) {
        this.salaId = salaId;
    }
    public Double getNormalPrice() {
        return normalPrice;
    }
    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }
    public Double getVipPrice() {
        return vipPrice;
    }
    public void setVipPrice(Double vipPrice) {
        this.vipPrice = vipPrice;
    }


}
