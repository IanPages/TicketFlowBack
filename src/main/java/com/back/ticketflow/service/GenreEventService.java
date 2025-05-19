package com.back.ticketflow.service;

import com.back.ticketflow.model.GenreEvent;
import com.back.ticketflow.repository.GenreEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreEventService {

    private GenreEventRepository genreEventRepository;

    @Autowired
    public GenreEventService(GenreEventRepository genreEventRepository) {
        this.genreEventRepository = genreEventRepository;
    }

    public List<GenreEvent> getAllGenres() {
        return genreEventRepository.findAll();
    }

    public GenreEvent getGenreById(Integer id) {
        return genreEventRepository.findById(id).orElse(null);
    }

    public GenreEvent saveGenre(GenreEvent genre) {
        return genreEventRepository.save(genre);
    }
    public GenreEvent updateGenre(Integer id, GenreEvent genre) {
        return genreEventRepository.findById(id)
                .map(existingGenre -> {
                    existingGenre.setName(genre.getName());
                    return genreEventRepository.save(existingGenre);
                })
                .orElse(null);
    }
    public boolean deleteGenre(Integer id) {
        if (genreEventRepository.existsById(id)) {
            genreEventRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
