package com.apps.filmtrackappv2.service.TMDB;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Video {
    private String id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String site;

    @NotNull
    @Size(max = 255)
    private String type;

    @NotNull
    private Integer size;

    @NotNull
    private Boolean official;

    @NotNull
    @Size(max = 255)
    private String key;

    @JsonProperty("published_at")
    @NotNull
    private String publishedAt;

    @NotNull
    private Long movie;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

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

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(final String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Long getMovie() {
        return movie;
    }

    public void setMovie(final Long movie) {
        this.movie = movie;
    }
}
