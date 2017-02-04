package com.floatingmuseum.mocloud.data.entity;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class Person implements Parcelable{

    private String name;
    private Ids ids;
    private Image images;
    private String biography;
    private String birthday;
    private String death;
    private String birthplace;
    private String homepage;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeParcelable(this.ids, flags);
        dest.writeParcelable(this.images, flags);
        dest.writeString(this.biography);
        dest.writeString(this.birthday);
        dest.writeString(this.death);
        dest.writeString(this.birthplace);
        dest.writeString(this.homepage);
    }

    public Person() {
    }

    protected Person(Parcel in) {
        this.name = in.readString();
        this.ids = in.readParcelable(Ids.class.getClassLoader());
        this.images = in.readParcelable(Image.class.getClassLoader());
        this.biography = in.readString();
        this.birthday = in.readString();
        this.death = in.readString();
        this.birthplace = in.readString();
        this.homepage = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
