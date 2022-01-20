package com.apps.filmtrackappv2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Movies {
    private int page;

    private List<MovieDTOResponse> results;

    @JsonProperty("total_results")
    private long totalResults;

    @JsonProperty("total_pages")
    private int totalPages;

    @JsonProperty("page_size")
    private int pageSize;

    public Movies() {
    }

    public Movies(int page, List<MovieDTOResponse> results, long total_results, int total_pages, int pageSize) {
        this.page = page;
        this.results = results;
        this.totalResults = total_results;
        this.totalPages = total_pages;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieDTOResponse> getResults() {
        return results;
    }

    public void setResults(List<MovieDTOResponse> results) {
        this.results = results;
    }

    public long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(long totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
