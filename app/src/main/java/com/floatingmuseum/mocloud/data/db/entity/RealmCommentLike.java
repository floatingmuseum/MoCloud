package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/27.
 */
@RealmClass
public class RealmCommentLike implements RealmModel {
    private String liked_at;
    @PrimaryKey
    private Integer id;
    private Integer parent_id;
    private String created_at;
    private String updated_at;
    private String comment;
    private Boolean spoiler;
    private Boolean review;
    private Integer replies;
    private Integer likes;
    private Integer user_rating;

    private String username;
    private Boolean private_user;
    //if user is private,this will be null
    private String name;
    //if user is private,this will be null
    private Boolean vip;
    //if user is private,this will be null
    private Boolean vip_ep;
    private String user_slug;

    public String getLiked_at() {
        return liked_at;
    }

    public void setLiked_at(String liked_at) {
        this.liked_at = liked_at;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent_id() {
        return parent_id;
    }

    public void setParent_id(Integer parent_id) {
        this.parent_id = parent_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Boolean spoiler) {
        this.spoiler = spoiler;
    }

    public Boolean getReview() {
        return review;
    }

    public void setReview(Boolean review) {
        this.review = review;
    }

    public Integer getReplies() {
        return replies;
    }

    public void setReplies(Integer replies) {
        this.replies = replies;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(Integer user_rating) {
        this.user_rating = user_rating;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getPrivate_user() {
        return private_user;
    }

    public void setPrivate_user(Boolean private_user) {
        this.private_user = private_user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getVip() {
        return vip;
    }

    public void setVip(Boolean vip) {
        this.vip = vip;
    }

    public Boolean getVip_ep() {
        return vip_ep;
    }

    public void setVip_ep(Boolean vip_ep) {
        this.vip_ep = vip_ep;
    }

    public String getUser_slug() {
        return user_slug;
    }

    public void setUser_slug(String user_slug) {
        this.user_slug = user_slug;
    }
}
