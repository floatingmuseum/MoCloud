package com.floatingmuseum.mocloud.model.entity;

/**
 * Created by Floatingmuseum on 2016/4/18.
 */
public class Trending {
    private int watchers;
    private Movie movie;

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
