package com.apps.filmtrackappv2.service.TMDB;

public class VideosResult {
    private int id;
    private Video[] results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Video[] getResults() {
        return results;
    }

    public void setResults(Video[] results) {
        this.results = results;
    }
}
