package com.floatingmuseum.mocloud.data.entity;

import java.io.Serializable;

/**
 * Created by Floatingmuseum on 2016/4/18.
 */
public class Ids{
    private int trakt;
    private String slug;
    private String imdb;
    private int tmdb;
    private int tvrage;

    public int getTrakt() {
        return trakt;
    }

    public void setTrakt(int trakt) {
        this.trakt = trakt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImdb() {
        return imdb;
    }

    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    public int getTmdb() {
        return tmdb;
    }

    public void setTmdb(int tmdb) {
        this.tmdb = tmdb;
    }

    public int getTvrage() {
        return tvrage;
    }

    public void setTvrage(int tvrage) {
        this.tvrage = tvrage;
    }
}
