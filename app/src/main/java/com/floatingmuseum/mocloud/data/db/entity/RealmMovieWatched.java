package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/22.
 */

@RealmClass
public class RealmMovieWatched implements RealmModel {
    private String title;
    private Integer year;
    @PrimaryKey
    private Integer trakt_id;
    //about watched
    private Integer plays;
    private String last_watched_at;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getTrakt_id() {
        return trakt_id;
    }

    public void setTrakt_id(Integer trakt_id) {
        this.trakt_id = trakt_id;
    }

    public Integer getPlays() {
        return plays;
    }

    public void setPlays(Integer plays) {
        this.plays = plays;
    }

    public String getLast_watched_at() {
        return last_watched_at;
    }

    public void setLast_watched_at(String last_watched_at) {
        this.last_watched_at = last_watched_at;
    }
}
