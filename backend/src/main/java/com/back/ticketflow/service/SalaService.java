package com.back.ticketflow.service;


import com.back.ticketflow.model.Sala;
import com.back.ticketflow.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SalaService {

    private SalaRepository salaRepository;

    @Autowired
    public SalaService(SalaRepository salaRepository){
        this.salaRepository = salaRepository;
    }

    public List<Sala> getAllSalas(){
        return salaRepository.findAll();
    }

    public Sala getSalaById(Integer id){
        return salaRepository.findById(id).orElse(null);
    }

    public Sala getSalaByName(String name){
        return salaRepository.findByName(name).orElse(null);
    }

    public Sala saveSala(Sala sala){
        return salaRepository.save(sala);
    }

    public Sala updateSala(Integer id, Sala sala){
        Optional<Sala> optionalSala = salaRepository.findById(id);
        if (optionalSala.isPresent()){
            Sala existingSala = optionalSala.get();
            existingSala.setName(sala.getName());
            existingSala.setLocation(sala.getLocation());
            existingSala.setCapacity(sala.getCapacity());
            return salaRepository.save(existingSala);
        }
        return null;
    }

    public boolean deleteSala(Integer id){
        if(salaRepository.existsById(id)){
            salaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
