package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Floatingmuseum on 2016/8/10.
 */
public class Staff implements Parcelable{
    public static final String CAST_ITEM = "Actors";
    public static final String DIRECTING_ITEM = "Directing";
    public static final String WRITING_ITEM = "Writing";
    public static final String PRODUCTION_ITEM = "Production";
    public static final String EDITING_ITEM = "Editing";
    public static final String ART_ITEM = "Art";
    public static final String CAMERA_ITEM = "Camera";
    public static final String COSTUME_AND_MAKEUP_ITEM = "Costume & Make-Up";
    public static final String SOUND_ITEM = "Sound";
    public static final String VISUAL_EFFECTS_ITEM = "Visual Effects";
    public static final String LIGHTING_ITEM = "Lighting";
    public static final String CREW_ITEM = "Crew";

    private Person person;
    private String character;
    private String job;
    private String itemType;
    private TmdbPersonImage tmdbPersonImage;
    private Movie movie;

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TmdbPersonImage getTmdbPersonImage() {
        return tmdbPersonImage;
    }

    public void setTmdbPersonImage(TmdbPersonImage tmdbPersonImage) {
        this.tmdbPersonImage = tmdbPersonImage;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Staff() {
    }

    @Override
    public String toString() {
        return "Staff{" +
                "person=" + person +
                ", character='" + character + '\'' +
                ", job='" + job + '\'' +
                ", itemType='" + itemType + '\'' +
                ", tmdbPersonImage=" + tmdbPersonImage +
                ", movie=" + movie +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.person, flags);
        dest.writeString(this.character);
        dest.writeString(this.job);
        dest.writeString(this.itemType);
        dest.writeParcelable(this.tmdbPersonImage, flags);
        dest.writeParcelable(this.movie, flags);
    }

    protected Staff(Parcel in) {
        this.person = in.readParcelable(Person.class.getClassLoader());
        this.character = in.readString();
        this.job = in.readString();
        this.itemType = in.readString();
        this.tmdbPersonImage = in.readParcelable(TmdbPersonImage.class.getClassLoader());
        this.movie = in.readParcelable(Movie.class.getClassLoader());
    }

    public static final Creator<Staff> CREATOR = new Creator<Staff>() {
        @Override
        public Staff createFromParcel(Parcel source) {
            return new Staff(source);
        }

        @Override
        public Staff[] newArray(int size) {
            return new Staff[size];
        }
    };
}
