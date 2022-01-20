package com.apps.filmtrackappv2.service;

import com.apps.filmtrackappv2.domain.Genre;
import com.apps.filmtrackappv2.model.GenreDTO;
import com.apps.filmtrackappv2.repos.GenreRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(final GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDTO> findAll() {
        return genreRepository.findAll()
                .stream()
                .map(genre -> mapToDTO(genre, new GenreDTO()))
                .collect(Collectors.toList());
    }

    public GenreDTO get(final Long id) {
        return genreRepository.findById(id)
                .map(genre -> mapToDTO(genre, new GenreDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final GenreDTO genreDTO) {
        final Genre genre = new Genre();
        mapToEntity(genreDTO, genre);
        return genreRepository.save(genre).getId();
    }

    public void update(final Long id, final GenreDTO genreDTO) {
        final Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(genreDTO, genre);
        genreRepository.save(genre);
    }

    public void delete(final Long id) {
        genreRepository.deleteById(id);
    }

    private GenreDTO mapToDTO(final Genre genre, final GenreDTO genreDTO) {
        genreDTO.setId(genre.getId());
        genreDTO.setName(genre.getName());
        return genreDTO;
    }

    private Genre mapToEntity(final GenreDTO genreDTO, final Genre genre) {
        genre.setName(genreDTO.getName());
        genre.setId(genreDTO.getId());
        return genre;
    }

}
