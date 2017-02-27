package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/26.
 */

public class BaseUserLike {
    private String liked_at;
    private String type;

    public String getLiked_at() {
        return liked_at;
    }

    public void setLiked_at(String liked_at) {
        this.liked_at = liked_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
