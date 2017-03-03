package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/3/2.
 */

public class MovieSyncItem extends Movie {
    private String watched_at;
    private String collected_at;
    private String listed_at;

    public String getWatched_at() {
        return watched_at;
    }

    public void setWatched_at(String watched_at) {
        this.watched_at = watched_at;
    }

    public String getCollected_at() {
        return collected_at;
    }

    public void setCollected_at(String collected_at) {
        this.collected_at = collected_at;
    }

    public String getListed_at() {
        return listed_at;
    }

    public void setListed_at(String listed_at) {
        this.listed_at = listed_at;
    }
}
