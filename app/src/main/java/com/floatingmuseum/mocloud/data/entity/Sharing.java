package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Floatingmuseum on 2016/12/22.
 */

public class Sharing implements Parcelable{

    private boolean facebook;
    private boolean twitter;
    private boolean tumblr;
    private boolean medium;

    public boolean isFacebook() {
        return facebook;
    }

    public void setFacebook(boolean facebook) {
        this.facebook = facebook;
    }

    public boolean isTwitter() {
        return twitter;
    }

    public void setTwitter(boolean twitter) {
        this.twitter = twitter;
    }

    public boolean isTumblr() {
        return tumblr;
    }

    public void setTumblr(boolean tumblr) {
        this.tumblr = tumblr;
    }

    public boolean isMedium() {
        return medium;
    }

    public void setMedium(boolean medium) {
        this.medium = medium;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.facebook ? (byte) 1 : (byte) 0);
        dest.writeByte(this.twitter ? (byte) 1 : (byte) 0);
        dest.writeByte(this.tumblr ? (byte) 1 : (byte) 0);
        dest.writeByte(this.medium ? (byte) 1 : (byte) 0);
    }

    public Sharing() {
    }

    protected Sharing(Parcel in) {
        this.facebook = in.readByte() != 0;
        this.twitter = in.readByte() != 0;
        this.tumblr = in.readByte() != 0;
        this.medium = in.readByte() != 0;
    }

    public static final Creator<Sharing> CREATOR = new Creator<Sharing>() {
        @Override
        public Sharing createFromParcel(Parcel source) {
            return new Sharing(source);
        }

        @Override
        public Sharing[] newArray(int size) {
            return new Sharing[size];
        }
    };
}
