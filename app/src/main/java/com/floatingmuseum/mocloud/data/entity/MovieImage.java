package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/11/22.
 */

public class MovieImage implements Parcelable{

    private String name;
    private String tmdb_id;
    private String imdb_id;
    private List<BaseFanart> hdmovieclearart;
    private List<BaseFanart> hdmovielogo;
    private List<BaseFanart> movieposter;
    private List<BaseFanart> moviebackground;
    private List<Moviedisc> moviedisc;
    private List<BaseFanart> moviebanner;
    private List<BaseFanart> moviethumb;

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

    public List<BaseFanart> getHdmovielogo() {
        return hdmovielogo;
    }

    public void setHdmovielogo(List<BaseFanart> hdmovielogo) {
        this.hdmovielogo = hdmovielogo;
    }

    public List<BaseFanart> getMoviethumb() {
        return moviethumb;
    }

    public void setMoviethumb(List<BaseFanart> moviethumb) {
        this.moviethumb = moviethumb;
    }

    public List<BaseFanart> getMoviebanner() {
        return moviebanner;
    }

    public void setMoviebanner(List<BaseFanart> moviebanner) {
        this.moviebanner = moviebanner;
    }

    public List<Moviedisc> getMoviedisc() {
        return moviedisc;
    }

    public void setMoviedisc(List<Moviedisc> moviedisc) {
        this.moviedisc = moviedisc;
    }

    public List<BaseFanart> getMoviebackground() {
        return moviebackground;
    }

    public void setMoviebackground(List<BaseFanart> moviebackground) {
        this.moviebackground = moviebackground;
    }

    public List<BaseFanart> getMovieposter() {
        return movieposter;
    }

    public void setMovieposter(List<BaseFanart> movieposter) {
        this.movieposter = movieposter;
    }

    public List<BaseFanart> getHdmovieclearart() {
        return hdmovieclearart;
    }

    public void setHdmovieclearart(List<BaseFanart> hdmovieclearart) {
        this.hdmovieclearart = hdmovieclearart;
    }

    public static class Moviedisc extends BaseFanart implements Parcelable{
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeString(this.disc);
            dest.writeString(this.disc_type);
        }

        protected Moviedisc(Parcel in) {
            super(in);
            this.disc = in.readString();
            this.disc_type = in.readString();
        }

        public static final Creator<Moviedisc> CREATOR = new Creator<Moviedisc>() {
            @Override
            public Moviedisc createFromParcel(Parcel source) {
                return new Moviedisc(source);
            }

            @Override
            public Moviedisc[] newArray(int size) {
                return new Moviedisc[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.tmdb_id);
        dest.writeString(this.imdb_id);
        dest.writeTypedList(this.hdmovieclearart);
        dest.writeTypedList(this.hdmovielogo);
        dest.writeTypedList(this.movieposter);
        dest.writeTypedList(this.moviebackground);
        dest.writeTypedList(this.moviedisc);
        dest.writeTypedList(this.moviebanner);
        dest.writeTypedList(this.moviethumb);
    }

    public MovieImage() {
    }

    protected MovieImage(Parcel in) {
        this.name = in.readString();
        this.tmdb_id = in.readString();
        this.imdb_id = in.readString();
        this.hdmovieclearart = in.createTypedArrayList(BaseFanart.CREATOR);
        this.hdmovielogo = in.createTypedArrayList(BaseFanart.CREATOR);
        this.movieposter = in.createTypedArrayList(BaseFanart.CREATOR);
        this.moviebackground = in.createTypedArrayList(BaseFanart.CREATOR);
        this.moviedisc = in.createTypedArrayList(Moviedisc.CREATOR);
        this.moviebanner = in.createTypedArrayList(BaseFanart.CREATOR);
        this.moviethumb = in.createTypedArrayList(BaseFanart.CREATOR);
    }

    public static final Creator<MovieImage> CREATOR = new Creator<MovieImage>() {
        @Override
        public MovieImage createFromParcel(Parcel source) {
            return new MovieImage(source);
        }

        @Override
        public MovieImage[] newArray(int size) {
            return new MovieImage[size];
        }
    };
}
