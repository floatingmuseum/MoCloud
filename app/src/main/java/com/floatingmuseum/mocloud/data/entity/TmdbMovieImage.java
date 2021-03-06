package com.floatingmuseum.mocloud.data.entity;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;
import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/11/27.
 */

public class TmdbMovieImage extends TmdbImage implements Parcelable {

    private Uri fileUri;
    private List<Backdrops> backdrops;
    private List<Posters> posters;

    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public List<Backdrops> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<Backdrops> backdrops) {
        this.backdrops = backdrops;
    }

    public List<Posters> getPosters() {
        return posters;
    }

    public void setPosters(List<Posters> posters) {
        this.posters = posters;
    }

    public static class Backdrops implements Parcelable{
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

        public Backdrops() {
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

        protected Backdrops(Parcel in) {
            this.aspect_ratio = in.readDouble();
            this.file_path = in.readString();
            this.height = in.readInt();
            this.iso_639_1 = in.readString();
            this.vote_average = in.readDouble();
            this.vote_count = in.readInt();
            this.width = in.readInt();
        }

        public static final Creator<Backdrops> CREATOR = new Creator<Backdrops>() {
            @Override
            public Backdrops createFromParcel(Parcel source) {
                return new Backdrops(source);
            }

            @Override
            public Backdrops[] newArray(int size) {
                return new Backdrops[size];
            }
        };
    }

    public static class Posters implements Parcelable{
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

        public Posters() {
        }

        protected Posters(Parcel in) {
            this.aspect_ratio = in.readDouble();
            this.file_path = in.readString();
            this.height = in.readInt();
            this.iso_639_1 = in.readString();
            this.vote_average = in.readDouble();
            this.vote_count = in.readInt();
            this.width = in.readInt();
        }

        public static final Creator<Posters> CREATOR = new Creator<Posters>() {
            @Override
            public Posters createFromParcel(Parcel source) {
                return new Posters(source);
            }

            @Override
            public Posters[] newArray(int size) {
                return new Posters[size];
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
        dest.writeParcelable(this.fileUri, flags);
        dest.writeTypedList(this.backdrops);
        dest.writeTypedList(this.posters);
    }

    public TmdbMovieImage() {
    }

    protected TmdbMovieImage(Parcel in) {
        super(in);
        this.fileUri = in.readParcelable(Uri.class.getClassLoader());
        this.backdrops = in.createTypedArrayList(Backdrops.CREATOR);
        this.posters = in.createTypedArrayList(Posters.CREATOR);
    }

    public static final Creator<TmdbMovieImage> CREATOR = new Creator<TmdbMovieImage>() {
        @Override
        public TmdbMovieImage createFromParcel(Parcel source) {
            return new TmdbMovieImage(source);
        }

        @Override
        public TmdbMovieImage[] newArray(int size) {
            return new TmdbMovieImage[size];
        }
    };
}
