package com.floatingmuseum.mocloud.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/8/11.
 */
public class Comment implements Parcelable {
    private long id;
    private String comment;
    private boolean spoiler;
    private boolean review;
    private long parent_id;
    private String created_at;
    private String updated_at;
    private int replies;
    private int likes;
    private Integer user_rating;
    private User user;
    private Sharing sharing;
    private Movie movie;

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

    public Integer getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(Integer user_rating) {
        this.user_rating = user_rating;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Sharing getSharing() {
        return sharing;
    }

    public void setSharing(Sharing sharing) {
        this.sharing = sharing;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.comment);
        dest.writeByte(this.spoiler ? (byte) 1 : (byte) 0);
        dest.writeByte(this.review ? (byte) 1 : (byte) 0);
        dest.writeLong(this.parent_id);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeInt(this.replies);
        dest.writeInt(this.likes);
        dest.writeInt(this.user_rating);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.sharing, flags);
        dest.writeParcelable(this.movie, flags);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.id = in.readLong();
        this.comment = in.readString();
        this.spoiler = in.readByte() != 0;
        this.review = in.readByte() != 0;
        this.parent_id = in.readLong();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.replies = in.readInt();
        this.likes = in.readInt();
        this.user_rating = in.readInt();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.sharing = in.readParcelable(Sharing.class.getClassLoader());
        this.movie = in.readParcelable(Movie.class.getClassLoader());
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
