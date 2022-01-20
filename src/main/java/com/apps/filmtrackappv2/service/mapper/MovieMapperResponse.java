package com.apps.filmtrackappv2.service.mapper;

import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.model.MovieDTOResponse;
import org.mapstruct.Mapper;


// https://www.baeldung.com/mapstruct
// https://mapstruct.org/
// we obtain an instance of a mapper in MapStruct bu using dependency injection
// instead of Mappers.getMapper
@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface MovieMapperResponse extends EntityMapper<MovieDTOResponse, Movie> {

}

