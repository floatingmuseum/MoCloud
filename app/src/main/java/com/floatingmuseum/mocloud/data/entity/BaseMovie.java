package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/4/24.
 */
public class BaseMovie {
    private int watchers;
    private Movie movie;
    private int watcher_count;
    private int play_count;
    private int collected_count;
    private int list_count;
    private int revenue;

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }


    public int getList_count() {
        return list_count;
    }

    public void setList_count(int list_count) {
        this.list_count = list_count;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public int getCollected_count() {
        return collected_count;
    }

    public void setCollected_count(int collected_count) {
        this.collected_count = collected_count;
    }

    public int getWatcher_count() {
        return watcher_count;
    }

    public void setWatcher_count(int watcher_count) {
        this.watcher_count = watcher_count;
    }

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
