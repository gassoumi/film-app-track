package com.apps.filmtrackappv2.repos;

import com.apps.filmtrackappv2.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GenreRepository extends JpaRepository<Genre, Long> {
}
