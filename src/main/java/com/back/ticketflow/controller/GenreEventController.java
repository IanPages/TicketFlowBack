package com.back.ticketflow.controller;

import com.back.ticketflow.model.GenreEvent;
import com.back.ticketflow.service.GenreEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genreEvents")
public class GenreEventController {

    private GenreEventService genreEventService;

    @Autowired
    public GenreEventController(GenreEventService genreEventService) {
        this.genreEventService = genreEventService;
    }

    @GetMapping
    public List<GenreEvent> getAllGenres() {
        return genreEventService.getAllGenres();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreEvent> getGenreById(@PathVariable Integer id) {
        GenreEvent genre = genreEventService.getGenreById(id);
        if (genre != null) {
            return ResponseEntity.ok(genre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping
    public ResponseEntity<GenreEvent> createGenre(@RequestBody GenreEvent genre) {
        GenreEvent createdGenre = genreEventService.saveGenre(genre);
        return ResponseEntity.ok(createdGenre);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenreEvent> updateGenre(@PathVariable Integer id, @RequestBody GenreEvent genre) {
        GenreEvent updatedGenre = genreEventService.updateGenre(id, genre);
        if (updatedGenre != null) {
            return ResponseEntity.ok(updatedGenre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable Integer id) {
        boolean deleted = genreEventService.deleteGenre(id);
        if (deleted) {
            return ResponseEntity.ok("Se ha eliminado este género");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
