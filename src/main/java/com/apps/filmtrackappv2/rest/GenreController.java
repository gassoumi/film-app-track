package com.apps.filmtrackappv2.rest;

import com.apps.filmtrackappv2.model.GenreDTO;
import com.apps.filmtrackappv2.service.GenreService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/genres", produces = MediaType.APPLICATION_JSON_VALUE)
public class GenreController {

    private final GenreService genreService;

    public GenreController(final GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return ResponseEntity.ok(genreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenre(@PathVariable final Long id) {
        return ResponseEntity.ok(genreService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createGenre(@RequestBody @Valid final GenreDTO genreDTO) {
        return new ResponseEntity<>(genreService.create(genreDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateGenre(@PathVariable final Long id,
            @RequestBody @Valid final GenreDTO genreDTO) {
        genreService.update(id, genreDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable final Long id) {
        genreService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
