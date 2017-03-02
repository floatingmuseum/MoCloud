package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/3/2.
 */

public class MovieHistorySyncItem extends Movie {
    private String watched_at;
    public String getWatched_at() {
        return watched_at;
    }

    public void setWatched_at(String watched_at) {
        this.watched_at = watched_at;
    }
}
