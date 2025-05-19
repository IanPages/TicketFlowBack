package com.back.ticketflow.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    private String image1;
    private String image2;

    private Date date;
    @Column(nullable = false)
    private String location;
    private Integer capacity;

    private Double normalPrice;
    private Double vipPrice;

    private Integer normalCapacity;
    private Integer vipCapacity;

    @ManyToOne
    @JoinColumn(name = "genre_id", nullable = false)
    private GenreEvent genre;

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Seat> seats;

    public Event(){}

    public Event(Integer id, String name, String description, String image2, String image1, Date date, String location, Integer capacity, Double normalPrice, Double vipPrice, Integer normalCapacity, Integer vipCapacity, GenreEvent genre, Sala sala, List<Seat> seats) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image2 = image2;
        this.image1 = image1;
        this.date = date;
        this.location = location;
        this.capacity = capacity;
        this.normalPrice = normalPrice;
        this.vipPrice = vipPrice;
        this.normalCapacity = normalCapacity;
        this.vipCapacity = vipCapacity;
        this.genre = genre;
        this.sala = sala;
        this.seats = seats;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImage1() {
        return image1;
    }
    public void setImage1(String image1) {
        this.image1 = image1;
    }
    public String getImage2() {
        return image2;
    }
    public void setImage2(String image2) {
        this.image2 = image2;
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
    public GenreEvent getGenre() {
        return genre;
    }
    public void setGenre(GenreEvent genre) {
        this.genre = genre;
    }
    public Sala getSala() {
        return sala;
    }
    public void setSala(Sala sala) {
        this.sala = sala;
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
    public Integer getNormalCapacity() {
        return normalCapacity;
    }
    public void setNormalCapacity(Integer normalCapacity) {
        this.normalCapacity = normalCapacity;
    }
    public Integer getVipCapacity() {
        return vipCapacity;
    }
    public void setVipCapacity(Integer vipCapacity) {
        this.vipCapacity = vipCapacity;
    }
    public List<Seat> getSeats() {
        return seats;
    }
    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
