package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class MovieWatchedItem {
    private int plays;
    private String last_watched_at;
    private Movie movie;

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public String getLast_watched_at() {
        return last_watched_at;
    }

    public void setLast_watched_at(String last_watched_at) {
        this.last_watched_at = last_watched_at;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
