package com.apps.filmtrackappv2.rest;

import com.apps.filmtrackappv2.model.MovieDTORequest;
import com.apps.filmtrackappv2.model.MovieDTOResponse;
import com.apps.filmtrackappv2.model.Movies;
import com.apps.filmtrackappv2.service.MovieService;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

	private final MovieService movieService;
	private static final Logger log = LoggerFactory.getLogger(MovieController.class);

	public MovieController(final MovieService movieService) {
		this.movieService = movieService;
	}

	@GetMapping
	public ResponseEntity<Movies> getAllMovies(@RequestParam(defaultValue = "1") Integer page,
											   @RequestParam(defaultValue = "10") Integer page_size, @RequestParam(defaultValue = "id") String sort_by, Pageable pageable) {
		var result = movieService.findAll(page, page_size, sort_by);
		Movies movies = new Movies(result.getNumber(), result.getContent(), result.getTotalElements(),
				result.getTotalPages(), result.getSize());

		return ResponseEntity.ok(movies);
	}

	@GetMapping("/similar/{id}")
	public ResponseEntity<List<MovieDTOResponse>> getSimilarMovies(@PathVariable final Long id) {
		var result = movieService.findAllSimilar(id).subList(0,9);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MovieDTOResponse> getMovie(@PathVariable final Long id) {
		return ResponseEntity.ok(movieService.get(id));
	}

	@PostMapping
	public ResponseEntity<Long> createMovie(@RequestBody @Valid final MovieDTORequest movieDTO) {
		return new ResponseEntity<>(movieService.create(movieDTO), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateMovie(@PathVariable final Long id,
			@RequestBody @Valid final MovieDTORequest movieDTO) {
		movieService.update(id, movieDTO);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteMovie(@PathVariable final Long id) {
		movieService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
