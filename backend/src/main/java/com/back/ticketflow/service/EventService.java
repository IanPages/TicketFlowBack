package com.back.ticketflow.service;

import com.back.ticketflow.dto.EventDTO;
import com.back.ticketflow.model.Event;
import com.back.ticketflow.model.GenreEvent;
import com.back.ticketflow.model.Sala;
import com.back.ticketflow.model.Seat;
import com.back.ticketflow.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private CloudinaryService cloudinaryService;
    private EventRepository eventRepository;
    private SalaService salaService;
    private GenreEventService genreService;
    private SeatService seatService;

    @Autowired
    public EventService(EventRepository eventRepository, CloudinaryService cloudinaryService,SalaService salaService, GenreEventService genreService, SeatService seatService) {
        this.eventRepository = eventRepository;
        this.cloudinaryService = cloudinaryService;
        this.salaService = salaService;
        this.genreService = genreService;
        this.seatService = seatService;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Integer id) {
        return eventRepository.findById(id).orElse(null);
    }
    public Event getEventByName(String name) {
        return eventRepository.findByName(name).orElse(null);
    }
    public Event saveEvent(EventDTO eventDTO, MultipartFile image1, MultipartFile image2) throws IOException {

        String image1Url = cloudinaryService.uploadFile(image1);
        String image2Url = cloudinaryService.uploadFile(image2);

        GenreEvent genreFound = genreService.getGenreById(eventDTO.getGenreId());
        if(genreFound == null){
            return null;
        }

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setImage1(image1Url);
        event.setImage2(image2Url);
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setGenre(genreFound);
        event.setNormalPrice(eventDTO.getNormalPrice());
        event.setVipPrice(eventDTO.getVipPrice() == null ? 0.0 : eventDTO.getVipPrice());

        if (eventDTO.getSalaId() != null) {
            Sala salaFound = salaService.getSalaById(eventDTO.getSalaId());
            if(salaFound == null){
                return null;
            }
            event.setCapacity(salaFound.getCapacity());
            event.setSala(salaFound);
             int vip = (int) Math.round(salaFound.getCapacity() * 0.2);
             int normal = salaFound.getCapacity() -vip;
             event.setVipCapacity(vip);
             event.setNormalCapacity(normal);

             if (salaFound.getNumberedSeats()){
                 List<Seat> seats = seatService.generateSeats(event, salaFound, normal,vip);
                 event.setSeats(seats);
             }
        } else{
            event.setCapacity(eventDTO.getCapacity());
            event.setVipCapacity(0);
            event.setNormalCapacity(eventDTO.getCapacity());
        }

        return eventRepository.save(event);
    }
    public Event updateEvent(Integer id, EventDTO eventDTO, MultipartFile image1, MultipartFile image2) throws IOException {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();

            event.setName(eventDTO.getName());
            event.setDescription(eventDTO.getDescription());

            if (image1 != null) {
                String image1Url = cloudinaryService.uploadFile(image1);
                event.setImage1(image1Url);
            }
            if (image2 != null) {
                String image2Url = cloudinaryService.uploadFile(image2);
                event.setImage2(image2Url);
            }

            event.setDate(eventDTO.getDate());
            event.setLocation(eventDTO.getLocation());

            GenreEvent genreFound = genreService.getGenreById(eventDTO.getGenreId());
            if (genreFound == null) return null;
            event.setGenre(genreFound);


            Sala newSala = eventDTO.getSalaId() != null ? salaService.getSalaById(eventDTO.getSalaId()) : null;

            if (eventDTO.getSalaId() != null) {
                if (newSala == null) return null;

                boolean salaChanged = event.getSala() == null || !event.getSala().getId().equals(newSala.getId());

                if (salaChanged) {
                    seatService.deleteSeatsByEventId(event.getId());

                    event.setSala(newSala);
                    event.setCapacity(newSala.getCapacity());

                    int vip = (int) Math.round(newSala.getCapacity() * 0.2);
                    int normal = newSala.getCapacity() - vip;

                    event.setVipCapacity(vip);
                    event.setNormalCapacity(normal);

                    if (newSala.getNumberedSeats()) {
                        List<Seat> newSeats = seatService.generateSeats(event, newSala, normal, vip);
                        event.setSeats(newSeats);
                    } else {
                        event.setSeats(null);
                    }
                }
            } else {

                event.setSala(null);
                event.setCapacity(eventDTO.getCapacity());
                event.setVipCapacity(0);
                event.setNormalCapacity(eventDTO.getCapacity());
                event.setSeats(null);
            }

            return eventRepository.save(event);
        }
        return null;
    }
    public boolean deleteEvent(Integer id) {
        if(eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
