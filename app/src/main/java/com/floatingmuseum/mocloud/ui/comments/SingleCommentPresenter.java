package com.floatingmuseum.mocloud.ui.comments;


import com.floatingmuseum.mocloud.base.BaseCommentsPresenter;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscription;

/**
 * Created by Floatingmuseum on 2016/12/20.
 */

public class SingleCommentPresenter extends BaseCommentsPresenter implements CommentsCallback<List<Comment>> {

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
    public void onSendCommentSuccess(Comment comment) {
        activity.onSendCommentSuccess(comment);
    }

    @Override
    public void onAddCommentToLikesSucceed(long commentId) {
        activity.onAddCommentToLikesSucceed(commentId);
    }

    @Override
    public void onRemoveCommentFromLikesSucceed(long commentId) {
        activity.onRemoveCommentFromLikesSucceed(commentId);
    }

    @Override
    public void onError(Throwable e) {

    }
}
