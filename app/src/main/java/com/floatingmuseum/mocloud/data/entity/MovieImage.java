package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/11/22.
 */

public class MovieImage implements Serializable{

    private String name;
    private String tmdb_id;
    private String imdb_id;
    private List<Hdmovieclearart> hdmovieclearart;
    private List<Hdmovielogo> hdmovielogo;
    private List<Movieposter> movieposter;
    private List<Moviebackground> moviebackground;
    private List<Moviedisc> moviedisc;
    private List<Moviebanner> moviebanner;
    private List<Moviethumb> moviethumb;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTmdb_id() {
        return tmdb_id;
    }

    public void setTmdb_id(String tmdb_id) {
        this.tmdb_id = tmdb_id;
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public List<Hdmovieclearart> getHdmovieclearart() {
        return hdmovieclearart;
    }

    public void setHdmovieclearart(List<Hdmovieclearart> hdmovieclearart) {
        this.hdmovieclearart = hdmovieclearart;
    }

    public List<Hdmovielogo> getHdmovielogo() {
        return hdmovielogo;
    }

    public void setHdmovielogo(List<Hdmovielogo> hdmovielogo) {
        this.hdmovielogo = hdmovielogo;
    }

    public List<Movieposter> getMovieposter() {
        return movieposter;
    }

    public void setMovieposter(List<Movieposter> movieposter) {
        this.movieposter = movieposter;
    }

    public List<Moviebackground> getMoviebackground() {
        return moviebackground;
    }

    public void setMoviebackground(List<Moviebackground> moviebackground) {
        this.moviebackground = moviebackground;
    }

    public List<Moviedisc> getMoviedisc() {
        return moviedisc;
    }

    public void setMoviedisc(List<Moviedisc> moviedisc) {
        this.moviedisc = moviedisc;
    }

    public List<Moviebanner> getMoviebanner() {
        return moviebanner;
    }

    public void setMoviebanner(List<Moviebanner> moviebanner) {
        this.moviebanner = moviebanner;
    }

    public List<Moviethumb> getMoviethumb() {
        return moviethumb;
    }

    public void setMoviethumb(List<Moviethumb> moviethumb) {
        this.moviethumb = moviethumb;
    }

    public static class Hdmovieclearart extends BaseFanart implements Serializable {
    }

    public static class Hdmovielogo extends BaseFanart implements Serializable{
    }

    public static class Movieposter extends BaseFanart implements Serializable{
    }

    public static class Moviebackground extends BaseFanart implements Serializable{
    }

    public static class Moviedisc extends BaseFanart implements Serializable{
        private String disc;
        private String disc_type;

        public String getDisc() {
            return disc;
        }

        public void setDisc(String disc) {
            this.disc = disc;
        }

        public String getDisc_type() {
            return disc_type;
        }

        public void setDisc_type(String disc_type) {
            this.disc_type = disc_type;
        }
    }

    public static class Moviebanner extends BaseFanart implements Serializable{
    }

    public static class Moviethumb extends BaseFanart implements Serializable{
    }
}
