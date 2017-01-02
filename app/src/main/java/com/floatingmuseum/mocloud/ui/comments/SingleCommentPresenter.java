package com.floatingmuseum.mocloud.ui.comments;


import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.CommentReplyCallback;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Reply;

import java.util.List;

import rx.Subscription;

/**
 * Created by Floatingmuseum on 2016/12/20.
 */

public class SingleCommentPresenter extends Presenter implements CommentReplyCallback<List<Comment>> {

    private SingleCommentActivity activity;

    public SingleCommentPresenter(SingleCommentActivity activity){
        this.activity = activity;
    }

    public void getData(long commentId){
        Subscription subscription = repository.getCommentReplies(commentId,this);
        compositeSubscription.add(subscription);
    }

    public void sendReply(long id, Reply reply) {
        Subscription subscription = repository.sendReply(id,reply,this);
        compositeSubscription.add(subscription);
    }

    @Override
    public void onBaseDataSuccess(List<Comment> replies) {
        activity.onBaseDataSuccess(replies);
    }

    @Override
    public void onSendReplySuccess(Comment comment) {
        activity.onSendReplySuccess(comment);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void start(boolean shouldClean) {

    }
}
