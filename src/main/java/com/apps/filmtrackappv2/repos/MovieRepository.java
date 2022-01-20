package com.apps.filmtrackappv2.repos;

import com.apps.filmtrackappv2.domain.Genre;
import com.apps.filmtrackappv2.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findDistinctAllByGenresIn(Collection<Genre> ages);
}
