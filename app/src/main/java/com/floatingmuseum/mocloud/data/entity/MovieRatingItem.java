package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class MovieRatingItem {
    private String rated_at;
    private int rating;
    private String type;
    private Movie movie;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
