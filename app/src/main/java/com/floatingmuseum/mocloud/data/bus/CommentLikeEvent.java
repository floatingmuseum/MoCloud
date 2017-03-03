package com.floatingmuseum.mocloud.data.bus;

/**
 * Created by Floatingmuseum on 2017/3/3.
 */

public class CommentLikeEvent {
    public long commentId;
    public boolean isLike;
    public int likes;

    public CommentLikeEvent(long commentId, boolean isLike) {
        this.commentId = commentId;
        this.isLike = isLike;
    }

    public CommentLikeEvent(long commentId, boolean isLike, int likes) {
        this.commentId = commentId;
        this.isLike = isLike;
        this.likes = likes;
    }
}
