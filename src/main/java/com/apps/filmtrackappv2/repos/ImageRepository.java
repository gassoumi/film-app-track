package com.apps.filmtrackappv2.repos;

import com.apps.filmtrackappv2.domain.Image;
import com.apps.filmtrackappv2.domain.Movie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepository extends JpaRepository<Image, String> {
	List<Image> findAllByMovie(Movie movie);
}
