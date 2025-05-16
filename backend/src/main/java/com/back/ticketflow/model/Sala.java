package com.back.ticketflow.model;

import jakarta.persistence.*;

@Entity
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Integer capacity;
    private Boolean numberedSeats;

    private Integer filas;
    private Integer columnas;

    public Sala() {
    }
    public Sala(String name, String location, Integer capacity, Boolean numberedSeats, Integer filas, Integer columnas) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.numberedSeats = numberedSeats;
        this.filas = filas;
        this.columnas = columnas;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public Boolean getNumberedSeats() {
        return numberedSeats;
    }
    public void setNumberedSeats(Boolean numberedSeats) {
        this.numberedSeats = numberedSeats;
    }
    public Integer getFilas() {
        return filas;
    }
    public void setFilas(Integer filas) {
        this.filas = filas;
    }
    public Integer getColumnas() {
        return columnas;
    }
    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

}
