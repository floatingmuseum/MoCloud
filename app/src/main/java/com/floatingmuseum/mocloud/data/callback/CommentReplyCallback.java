package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Comment;

/**
 * Created by Floatingmuseum on 2016/12/21.
 */

public interface CommentReplyCallback<T> extends DataCallback<T> {
    void onSendReplySuccess(Comment comment);
}
