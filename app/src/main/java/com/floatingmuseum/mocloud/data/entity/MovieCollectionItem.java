package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class MovieCollectionItem {
    private String collected_at;
    private Movie movie;

    public String getCollected_at() {
        return collected_at;
    }

    public void setCollected_at(String collected_at) {
        this.collected_at = collected_at;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
