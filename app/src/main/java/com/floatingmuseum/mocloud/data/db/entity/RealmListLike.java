package com.floatingmuseum.mocloud.data.db.entity;


import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/27.
 */
@RealmClass
public class RealmListLike implements RealmModel {
    private String liked_at;
    private String list_name;
    private String description;
    private String privacy;
    private Boolean display_numbers;
    private Boolean allow_comments;
    private String updated_at;
    private Integer item_count;
    private Integer comment_count;
    private Integer likes;
    @PrimaryKey
    private Integer id;
    private String slug;

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

    public String getList_name() {
        return list_name;
    }

    public void setList_name(String list_name) {
        this.list_name = list_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    public Boolean getDisplay_numbers() {
        return display_numbers;
    }

    public void setDisplay_numbers(Boolean display_numbers) {
        this.display_numbers = display_numbers;
    }

    public Boolean getAllow_comments() {
        return allow_comments;
    }

    public void setAllow_comments(Boolean allow_comments) {
        this.allow_comments = allow_comments;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getItem_count() {
        return item_count;
    }

    public void setItem_count(Integer item_count) {
        this.item_count = item_count;
    }

    public Integer getComment_count() {
        return comment_count;
    }

    public void setComment_count(Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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
