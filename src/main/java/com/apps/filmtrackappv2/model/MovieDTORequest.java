package com.apps.filmtrackappv2.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class MovieDTORequest extends MovieAbstract {
    private List<Long> genres;

    public MovieDTORequest() {
    }

    public MovieDTORequest(Long id, @NotNull String title, @NotNull String overview, @NotNull LocalDate releaseDate, Integer voteCount, Double voteAverage,
                           @NotNull Boolean adult, String posterPath, String backdropPath, List<Long> genres) {
        super(id, title, overview, releaseDate, voteCount, voteAverage, adult, posterPath, backdropPath);
        this.genres = genres;
    }

    public List<Long> getGenres() {
        return genres;
    }

    public void setGenres(List<Long> genres) {
        this.genres = genres;
    }
}
