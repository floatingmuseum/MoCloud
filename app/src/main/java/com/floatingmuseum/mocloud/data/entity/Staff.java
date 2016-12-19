package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/8/10.
 */
public class Staff {
    private String job;
    private String character;
    private Person person;
    private Movie movie;
    private TmdbPeopleImage tmdbPeopleImage;

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
}
