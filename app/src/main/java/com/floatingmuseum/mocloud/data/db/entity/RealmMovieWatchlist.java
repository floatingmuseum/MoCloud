package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/22.
 */

@RealmClass
public class RealmMovieWatchlist implements RealmModel {

    private String title;
    private Integer year;
    @PrimaryKey
    private Integer trakt_id;
    //about watchlist
    private Integer rank;
    private String listed_at;

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

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getListed_at() {
        return listed_at;
    }

    public void setListed_at(String listed_at) {
        this.listed_at = listed_at;
    }
}
