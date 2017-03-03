package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Comment;

/**
 * Created by Floatingmuseum on 2016/9/2.
 */
public interface CommentsCallback<T> extends DataCallback<T> {
    void onSendCommentSuccess(Comment comment);

    void onAddCommentToLikesSucceed(long commentId);

    void onRemoveCommentFromLikesSucceed(long commentId);
}
