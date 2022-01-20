package com.apps.filmtrackappv2.domain;


import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import javax.persistence.*;


@Entity
public class Movie {

    @Id
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, unique = true, columnDefinition = "longtext")
    private String title;

    @Column(nullable = false, columnDefinition = "longtext")
    private String overview;

    @Column(nullable = false)
    private LocalDate releaseDate;

    @Column
    private Integer voteCount;

    @Column
    private Double voteAverage;

    @Column(nullable = false)
    private Boolean adult;

    @Column(columnDefinition = "longtext")
    private String posterPath;

    @Column(columnDefinition = "longtext")
    private String backdropPath;

    @OneToMany(mappedBy = "movie")
    private Set<Video> movieVideos;

    @ManyToMany(mappedBy = "favoriteMovieMovies")
    private Set<User> favoriteMovieUsers;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres;

    @OneToMany(mappedBy = "movie")
    private Set<Image> movieImages;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Movie() {
    }

    public Movie(Long id, String title, String overview, LocalDate releaseDate, Integer voteCount, Double voteAverage, Boolean adult, String posterPath, String backdropPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
        this.adult = adult;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
    }

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(final Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(final Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(final Boolean adult) {
        this.adult = adult;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(final String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(final String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Set<Video> getMovieVideos() {
        return movieVideos;
    }

    public void setMovieVideos(final Set<Video> movieVideos) {
        this.movieVideos = movieVideos;
    }

    public Set<User> getFavoriteMovieUsers() {
        return favoriteMovieUsers;
    }

    public void setFavoriteMovieUsers(final Set<User> favoriteMovieUsers) {
        this.favoriteMovieUsers = favoriteMovieUsers;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(final Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Image> getMovieImages() {
        return movieImages;
    }

    public void setMovieImages(final Set<Image> movieImages) {
        this.movieImages = movieImages;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Movie{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", overview='").append(overview).append('\'');
        sb.append(", releaseDate=").append(releaseDate);
        sb.append(", voteCount=").append(voteCount);
        sb.append(", voteAverage=").append(voteAverage);
        sb.append(", adult=").append(adult);
        sb.append(", posterPath='").append(posterPath).append('\'');
        sb.append(", backdropPath='").append(backdropPath).append('\'');
        sb.append(", genres=").append(genres);
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append('}');
        return sb.toString();
    }
}
