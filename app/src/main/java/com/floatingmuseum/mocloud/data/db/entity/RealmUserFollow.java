package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/3/27.
 */
@RealmClass
public class RealmUserFollow implements RealmModel {

    String username;
    @PrimaryKey
    String slug;
    Boolean isPrivate;
    Boolean following;
    Boolean follower;

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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Boolean getFollowing() {
        return following;
    }

    public void setFollowing(Boolean following) {
        this.following = following;
    }

    public Boolean getFollower() {
        return follower;
    }

    public void setFollower(Boolean follower) {
        this.follower = follower;
    }

    @Override
    public String toString() {
        return "RealmUserFollow{" +
                "username='" + username + '\'' +
                ", slug='" + slug + '\'' +
                ", isPrivate=" + isPrivate +
                ", following=" + following +
                ", follower=" + follower +
                '}';
    }
}
