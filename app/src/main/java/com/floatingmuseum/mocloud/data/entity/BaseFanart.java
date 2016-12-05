package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Floatingmuseum on 2016/11/23.
 */

public class BaseFanart implements Parcelable{
    private String id;
    private String url;
    private String lang;
    private String likes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.lang);
        dest.writeString(this.likes);
    }

    protected BaseFanart(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
        this.lang = in.readString();
        this.likes = in.readString();
    }

    public static final Creator<BaseFanart> CREATOR = new Creator<BaseFanart>() {
        @Override
        public BaseFanart createFromParcel(Parcel source) {
            return new BaseFanart(source);
        }

        @Override
        public BaseFanart[] newArray(int size) {
            return new BaseFanart[size];
        }
    };
}
