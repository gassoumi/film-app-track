package com.apps.filmtrackappv2.service.mapper;

import com.apps.filmtrackappv2.domain.Genre;
import com.apps.filmtrackappv2.model.GenreDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper extends EntityMapper<GenreDTO, Genre> {
}
