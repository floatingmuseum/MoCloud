package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/27.
 */
@RealmClass
public class RealmMovieCollection implements RealmModel {
    private String title;
    private Integer year;
    @PrimaryKey
    private Integer trakt_id;
    private String collected_at;

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

    public String getCollected_at() {
        return collected_at;
    }

    public void setCollected_at(String collected_at) {
        this.collected_at = collected_at;
    }
}
