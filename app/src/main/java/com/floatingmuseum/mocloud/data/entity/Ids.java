package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Floatingmuseum on 2016/4/18.
 */
public class Ids implements Parcelable{
    private int trakt;
    private String slug;
    private String imdb;
    private int tvdb;
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

    public int getTvdb() {
        return tvdb;
    }

    public void setTvdb(int tvdb) {
        this.tvdb = tvdb;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.trakt);
        dest.writeString(this.slug);
        dest.writeString(this.imdb);
        dest.writeInt(this.tvdb);
        dest.writeInt(this.tmdb);
        dest.writeInt(this.tvrage);
    }

    public Ids() {
    }

    protected Ids(Parcel in) {
        this.trakt = in.readInt();
        this.slug = in.readString();
        this.imdb = in.readString();
        this.tvdb = in.readInt();
        this.tmdb = in.readInt();
        this.tvrage = in.readInt();
    }

    public static final Creator<Ids> CREATOR = new Creator<Ids>() {
        @Override
        public Ids createFromParcel(Parcel source) {
            return new Ids(source);
        }

        @Override
        public Ids[] newArray(int size) {
            return new Ids[size];
        }
    };
}
