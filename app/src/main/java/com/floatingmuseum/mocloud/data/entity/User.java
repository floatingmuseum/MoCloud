package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Floatingmuseum on 2016/8/15.
 */
public class User implements Parcelable{
    private String username;//用户名
    @SerializedName("private")
    private boolean privateX;
    private String name;//昵称（昵称有时可能为null或者""）
    private boolean vip;
    private boolean vip_ep;
    private Ids ids;
    private String joined_at;
    private String location;
    private String about;
    private String gender;
    private int age;
    private Image images;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isPrivateX() {
        return privateX;
    }

    public void setPrivateX(boolean privateX) {
        this.privateX = privateX;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isVip_ep() {
        return vip_ep;
    }

    public void setVip_ep(boolean vip_ep) {
        this.vip_ep = vip_ep;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public String getJoined_at() {
        return joined_at;
    }

    public void setJoined_at(String joined_at) {
        this.joined_at = joined_at;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Image getImages() {
        return images;
    }

    public void setImages(Image images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeByte(this.privateX ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeByte(this.vip ? (byte) 1 : (byte) 0);
        dest.writeByte(this.vip_ep ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.ids, flags);
        dest.writeString(this.joined_at);
        dest.writeString(this.location);
        dest.writeString(this.about);
        dest.writeString(this.gender);
        dest.writeInt(this.age);
        dest.writeParcelable(this.images, flags);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.username = in.readString();
        this.privateX = in.readByte() != 0;
        this.name = in.readString();
        this.vip = in.readByte() != 0;
        this.vip_ep = in.readByte() != 0;
        this.ids = in.readParcelable(Ids.class.getClassLoader());
        this.joined_at = in.readString();
        this.location = in.readString();
        this.about = in.readString();
        this.gender = in.readString();
        this.age = in.readInt();
        this.images = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
