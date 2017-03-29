package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/3/28.
 */
@RealmClass
public class RealmFollowing implements RealmModel {

    private String username;
    @PrimaryKey
    private String slug;
    private String following_at;
    private Boolean isPrivate;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getFollowing_at() {
        return following_at;
    }

    public void setFollowing_at(String following_at) {
        this.following_at = following_at;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "RealmFollowing{" +
                "username='" + username + '\'' +
                ", slug='" + slug + '\'' +
                ", following_at='" + following_at + '\'' +
                ", isPrivate=" + isPrivate +
                '}';
    }
}
