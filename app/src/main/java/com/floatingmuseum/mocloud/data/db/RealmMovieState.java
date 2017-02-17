package com.floatingmuseum.mocloud.data.db;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/18.
 */

@RealmClass
public class RealmMovieState implements RealmModel{

    private String title;
    private int year;
    private int trakt_id;
    private int tmdb_Id;
    private String slug;
    private String imdb_id;

    private int plays;
    private String last_watched_at;

    private int rank;
    private String listed_at;

    private String collected_at;

    private String rated_at;
    private int rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getTrakt_id() {
        return trakt_id;
    }

    public void setTrakt_id(int trakt_id) {
        this.trakt_id = trakt_id;
    }

    public int getTmdb_Id() {
        return tmdb_Id;
    }

    public void setTmdb_Id(int tmdb_Id) {
        this.tmdb_Id = tmdb_Id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public String getLast_watched_at() {
        return last_watched_at;
    }

    public void setLast_watched_at(String last_watched_at) {
        this.last_watched_at = last_watched_at;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
