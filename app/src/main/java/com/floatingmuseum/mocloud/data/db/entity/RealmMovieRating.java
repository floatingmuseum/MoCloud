package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/27.
 */
@RealmClass
public class RealmMovieRating implements RealmModel{
    private String title;
    private Integer year;
    @PrimaryKey
    private Integer trakt_id;
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
