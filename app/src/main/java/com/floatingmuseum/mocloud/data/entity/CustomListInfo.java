package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/26.
 */

public class CustomListInfo {

    private String name;
    private String description;
    private String privacy;
    private boolean display_numbers;
    private boolean allow_comments;
    private String sort_by;
    private String sort_how;
    private String created_at;
    private String updated_at;
    private int item_count;
    private int comment_count;
    private int likes;
    private Ids ids;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isDisplay_numbers() {
        return display_numbers;
    }

    public void setDisplay_numbers(boolean display_numbers) {
        this.display_numbers = display_numbers;
    }

    public boolean isAllow_comments() {
        return allow_comments;
    }

    public void setAllow_comments(boolean allow_comments) {
        this.allow_comments = allow_comments;
    }

    public String getSort_by() {
        return sort_by;
    }

    public void setSort_by(String sort_by) {
        this.sort_by = sort_by;
    }

    public String getSort_how() {
        return sort_how;
    }

    public void setSort_how(String sort_how) {
        this.sort_how = sort_how;
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

    public int getItem_count() {
        return item_count;
    }

    public void setItem_count(int item_count) {
        this.item_count = item_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }
}
