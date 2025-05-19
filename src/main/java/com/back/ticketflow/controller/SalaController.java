package com.back.ticketflow.controller;


import com.back.ticketflow.model.Sala;
import com.back.ticketflow.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/salas")
public class SalaController {

    private SalaService salaService;

    @Autowired
    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public List<Sala> getAllSalas() {
        return salaService.getAllSalas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sala> getSalaById(@PathVariable Integer id) {
        Sala sala = salaService.getSalaById(id);
        if (sala != null) {
            return ResponseEntity.ok(sala);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/name/{name}")
    public ResponseEntity<Sala> getSalaByName(@PathVariable String name) {
        Sala sala = salaService.getSalaByName(name);
        if (sala != null) {
            return ResponseEntity.ok(sala);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Sala> createSala(@RequestBody Sala sala) {
        Sala createdSala = salaService.saveSala(sala);
        return ResponseEntity.ok(createdSala);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @PutMapping("/{id}")
    public ResponseEntity<Sala> updateSala(@PathVariable Integer id, @RequestBody Sala sala) {
        Sala updatedSala = salaService.updateSala(id, sala);
        if (updatedSala != null) {
            return ResponseEntity.ok(updatedSala);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSala(@PathVariable Integer id) {
        boolean deleted = salaService.deleteSala(id);
        if (deleted) {
            return ResponseEntity.ok("Se ha eliminado la sala");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
