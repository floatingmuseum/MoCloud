package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yan on 2016/8/11.
 */
public class Comment implements Serializable{
        private long id;
        private String comment;
        private boolean spoiler;
        private boolean review;
        private long parent_id;
        private String created_at;
        private String updated_at;
        private int replies;
        private int likes;
        private int user_rating;
        private User user;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public boolean isSpoiler() {
            return spoiler;
        }

        public void setSpoiler(boolean spoiler) {
            this.spoiler = spoiler;
        }

        public boolean isReview() {
            return review;
        }

        public void setReview(boolean review) {
            this.review = review;
        }

        public long getParent_id() {
            return parent_id;
        }

        public void setParent_id(long parent_id) {
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

        public int getReplies() {
            return replies;
        }

        public void setReplies(int replies) {
            this.replies = replies;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getUser_rating() {
            return user_rating;
        }

        public void setUser_rating(int user_rating) {
            this.user_rating = user_rating;
        }
}
