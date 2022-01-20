package com.apps.filmtrackappv2.repos;

import com.apps.filmtrackappv2.domain.Movie;
import com.apps.filmtrackappv2.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface VideoRepository extends JpaRepository<Video, Long> {
    List<Video> findAllByMovie(Movie movie);
}
