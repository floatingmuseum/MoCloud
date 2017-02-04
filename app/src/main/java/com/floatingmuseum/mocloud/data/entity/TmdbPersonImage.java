package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/8.
 */

public class TmdbPersonImage extends TmdbImage implements Parcelable{

    private List<Profiles> profiles;

    public List<Profiles> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profiles> profiles) {
        this.profiles = profiles;
    }

    public static class Profiles implements Parcelable{
        private double aspect_ratio;
        private String file_path;
        private int height;
        private String iso_639_1;
        private double vote_average;
        private int vote_count;
        private int width;

        public double getAspect_ratio() {
            return aspect_ratio;
        }

        public void setAspect_ratio(double aspect_ratio) {
            this.aspect_ratio = aspect_ratio;
        }

        public String getFile_path() {
            return file_path;
        }

        public void setFile_path(String file_path) {
            this.file_path = file_path;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getIso_639_1() {
            return iso_639_1;
        }

        public void setIso_639_1(String iso_639_1) {
            this.iso_639_1 = iso_639_1;
        }

        public double getVote_average() {
            return vote_average;
        }

        public void setVote_average(double vote_average) {
            this.vote_average = vote_average;
        }

        public int getVote_count() {
            return vote_count;
        }

        public void setVote_count(int vote_count) {
            this.vote_count = vote_count;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(this.aspect_ratio);
            dest.writeString(this.file_path);
            dest.writeInt(this.height);
            dest.writeString(this.iso_639_1);
            dest.writeDouble(this.vote_average);
            dest.writeInt(this.vote_count);
            dest.writeInt(this.width);
        }

        public Profiles() {
        }

        protected Profiles(Parcel in) {
            this.aspect_ratio = in.readDouble();
            this.file_path = in.readString();
            this.height = in.readInt();
            this.iso_639_1 = in.readString();
            this.vote_average = in.readDouble();
            this.vote_count = in.readInt();
            this.width = in.readInt();
        }

        public static final Creator<Profiles> CREATOR = new Creator<Profiles>() {
            @Override
            public Profiles createFromParcel(Parcel source) {
                return new Profiles(source);
            }

            @Override
            public Profiles[] newArray(int size) {
                return new Profiles[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.profiles);
    }

    public TmdbPersonImage() {
    }

    protected TmdbPersonImage(Parcel in) {
        super(in);
        this.profiles = in.createTypedArrayList(Profiles.CREATOR);
    }

    public static final Creator<TmdbPersonImage> CREATOR = new Creator<TmdbPersonImage>() {
        @Override
        public TmdbPersonImage createFromParcel(Parcel source) {
            return new TmdbPersonImage(source);
        }

        @Override
        public TmdbPersonImage[] newArray(int size) {
            return new TmdbPersonImage[size];
        }
    };
}
