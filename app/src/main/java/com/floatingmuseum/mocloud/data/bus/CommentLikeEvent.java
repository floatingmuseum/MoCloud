package com.floatingmuseum.mocloud.data.bus;

/**
 * Created by Floatingmuseum on 2017/3/3.
 */

public class CommentLikeEvent {
    public long commentId;
    public boolean isLike;

    public CommentLikeEvent(long commentId, boolean isLike) {
        this.commentId = commentId;
        this.isLike = isLike;
    }
}
