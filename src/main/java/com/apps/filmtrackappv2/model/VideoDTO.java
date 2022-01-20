package com.apps.filmtrackappv2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class VideoDTO {

    private String id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String site;

    @NotNull
    private Boolean official;

    @NotNull
    @Size(max = 255)
    private String key;

    @JsonProperty("published_at")
    @NotNull
    private LocalDateTime publishedAt;

    @NotNull
    private Long movie;


    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(final String site) {
        this.site = site;
    }

    public Boolean getOfficial() {
        return official;
    }

    public void setOfficial(final Boolean official) {
        this.official = official;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(final LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Long getMovie() {
        return movie;
    }

    public void setMovie(final Long movie) {
        this.movie = movie;
    }

}
