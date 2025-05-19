package com.back.ticketflow.controller;

import com.back.ticketflow.dto.EventDTO;
import com.back.ticketflow.model.Event;
import com.back.ticketflow.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Integer id) {
        Event event = eventService.getEventById(id);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }
    @GetMapping("/name/{eventName}")
    public ResponseEntity<Event> getEventByName(@PathVariable String eventName) {
        Event event = eventService.getEventByName(eventName);
        if (event == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(event);
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Event> createEvent(@RequestPart("event") String eventJson, @RequestPart("image1") MultipartFile image1, @RequestPart("image2") MultipartFile image2) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        EventDTO eventDTO = mapper.readValue(eventJson, EventDTO.class);

        Event createdEvent = eventService.saveEvent(eventDTO,image1,image2);
        if (createdEvent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(createdEvent);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Integer id, @RequestPart("event") String eventJson,
                                             @RequestPart(value = "image1", required = false) MultipartFile image1,
                                             @RequestPart(value = "image2", required = false) MultipartFile image2) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        EventDTO eventDTO = mapper.readValue(eventJson, EventDTO.class);

        Event updatedEvent = eventService.updateEvent(id, eventDTO,image1,image2);
        if (updatedEvent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedEvent);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Integer id) {
        if (eventService.deleteEvent(id)) {
            return ResponseEntity.ok("Evento eliminado");
        }
        return ResponseEntity.notFound().build();
    }



}
