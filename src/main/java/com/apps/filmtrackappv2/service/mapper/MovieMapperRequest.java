package com.apps.filmtrackappv2.service.mapper;

import com.apps.filmtrackappv2.domain.Genre;
import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.model.MovieDTORequest;
import com.apps.filmtrackappv2.repos.GenreRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// https://www.baeldung.com/mapstruct
// https://mapstruct.org/
// we obtain an instance of a mapper in MapStruct bu using dependency injection
// instead of Mappers.getMapper
@Mapper(componentModel = "spring")
public abstract class MovieMapperRequest implements EntityMapper<MovieDTORequest, Movie> {

    @Autowired
    private GenreRepository genreRepository;

    List<Long> map(Set<Genre> value) {
        return value == null ? null : value.stream()
                .map(genre -> genre.getId())
                .collect(Collectors.toList());
    }


    Set<Genre> map(List<Long> value) {
        if (value != null) {
            final List<Genre> genres = genreRepository.findAllById(value);
            if (genres.size() != value.size()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "one of genres not found");
            }
            return genres.stream().collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}

