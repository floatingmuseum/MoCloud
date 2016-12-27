package com.floatingmuseum.mocloud.data.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by Floatingmuseum on 2016/8/10.
 */
public class Staff {
    public static final int CAST_ITEM = 0;
    public static final int DIRECTING_ITEM = 1;
    public static final int WRITING_ITEM = 2;
    public static final int PRODUCTION_ITEM = 3;
    public static final int ART_ITEM = 4;
    public static final int CAMERA_ITEM = 5;
    public static final int COSTUME_AND_MAKEUP_ITEM = 6;
    public static final int SOUND_ITEM = 7;

    private String job;
    private String character;
    private Person person;
    private Movie movie;
    private TmdbPeopleImage tmdbPeopleImage;

    private int itemType;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public TmdbPeopleImage getTmdbPeopleImage() {
        return tmdbPeopleImage;
    }

    public void setTmdbPeopleImage(TmdbPeopleImage tmdbPeopleImage) {
        this.tmdbPeopleImage = tmdbPeopleImage;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
