package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2016/12/28.
 */

public class Follower {
    private String followed_at;
    private User user;

    public String getFollowed_at() {
        return followed_at;
    }

    public void setFollowed_at(String followed_at) {
        this.followed_at = followed_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
