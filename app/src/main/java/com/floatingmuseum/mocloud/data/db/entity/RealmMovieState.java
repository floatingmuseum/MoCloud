package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/18.
 */

@RealmClass
public class RealmMovieState implements RealmModel {

    private String title;
    private Integer year;
    @PrimaryKey
    private Integer trakt_id;

    //about watched
    private Integer plays;
    private String last_watched_at;

    //about watchlist
    private Integer rank;
    private String listed_at;

    //about collection
    private String collected_at;

    //about rating
    private String rated_at;
    private Integer rating;

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

    public String getCollected_at() {
        return collected_at;
    }

    public void setCollected_at(String collected_at) {
        this.collected_at = collected_at;
    }

    public String getRated_at() {
        return rated_at;
    }

    public void setRated_at(String rated_at) {
        this.rated_at = rated_at;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
