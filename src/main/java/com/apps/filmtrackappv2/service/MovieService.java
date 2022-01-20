package com.apps.filmtrackappv2.service;

import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.model.MovieDTORequest;
import com.apps.filmtrackappv2.model.MovieDTOResponse;
import com.apps.filmtrackappv2.repos.MovieRepository;

import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.apps.filmtrackappv2.service.mapper.MovieMapperRequest;


import com.apps.filmtrackappv2.service.mapper.MovieMapperResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Transactional
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    private final MovieMapperRequest movieMapperRequest;

    private final MovieMapperResponse movieMapperResponse;

    public MovieService(final MovieRepository movieRepository,
                        final MovieMapperRequest movieMapperRequest,
                        final MovieMapperResponse movieMapperResponse) {
        this.movieRepository = movieRepository;
        this.movieMapperRequest = movieMapperRequest;
        this.movieMapperResponse = movieMapperResponse;
    }

    public List<MovieDTOResponse> findAll() {
        return movieRepository.findAll()
                .stream()
                .map(movieMapperResponse::toDto)
                .collect(Collectors.toList());
    }

    public List<MovieDTOResponse> findAllSimilar(Long movieId) {
        final Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return movieRepository.findDistinctAllByGenresIn(movie.getGenres())
                .stream()
                .filter(movie1 -> movie1.getId() != movieId)
                .map(movieMapperResponse::toDto)
                .collect(Collectors.toList());
    }

    public Page<MovieDTOResponse> findAll(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return movieRepository.findAll(paging).map(movieMapperResponse::toDto);
    }

    public MovieDTOResponse get(final Long id) {
        return movieRepository.findById(id)
                .map(movieMapperResponse::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final MovieDTORequest movieDTO) {
        final Movie movie = movieMapperRequest.toEntity(movieDTO);
        return movieRepository.save(movie).getId();
    }

    public void update(final Long id, final MovieDTORequest movieDTO) {
        movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        final Movie movie = movieMapperRequest.toEntity(movieDTO);
        movie.setId(id);
        movieRepository.save(movie);
    }

    public void delete(final Long id) {
        movieRepository.deleteById(id);
    }


}
