package com.floatingmuseum.mocloud.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by yan on 2016/8/15.
 */
public class User implements Serializable{
    private String username;
    @SerializedName("private")
    private boolean privateX;
    private String name;
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
}
