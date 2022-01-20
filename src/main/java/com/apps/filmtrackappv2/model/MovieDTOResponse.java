package com.apps.filmtrackappv2.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class MovieDTOResponse extends MovieAbstract {

    private List<GenreDTO> genres;

    public MovieDTOResponse(Long id, @NotNull String title, @NotNull String overview, @NotNull LocalDate releaseDate, Integer voteCount,
                            Double voteAverage, @NotNull Boolean adult, String posterPath, String backdropPath, List<GenreDTO> genres) {
        super(id, title, overview, releaseDate, voteCount, voteAverage, adult, posterPath, backdropPath);
        this.genres = genres;
    }

    public List<GenreDTO> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDTO> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MovieDTOResponse{");
        sb.append("genres=").append(genres);
        sb.append('}');
        return sb.toString();
    }
}
