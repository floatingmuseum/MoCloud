package com.floatingmuseum.mocloud.ui.comments;


import com.floatingmuseum.mocloud.base.BaseCommentsPresenter;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by Floatingmuseum on 2016/12/20.
 */

public class SingleCommentPresenter extends BaseCommentsPresenter implements CommentsCallback<List<Comment>> {

    private SingleCommentActivity activity;

    public SingleCommentPresenter(SingleCommentActivity activity){
        this.activity = activity;
    }

    public void getData(long commentId){
        Disposable disposable = repository.getCommentReplies(commentId,this);
        compositeDisposable.remove(disposable);
    }

    public void sendReply(long id, Reply reply) {
        Disposable disposable = repository.sendReply(id,reply,this);
        compositeDisposable.add(disposable);
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
    public void onAddCommentToLikesSuccess(long commentId) {
        activity.onAddCommentToLikesSuccess(commentId);
    }

    @Override
    public void onRemoveCommentFromLikesSuccess(long commentId) {
        activity.onRemoveCommentFromLikesSuccess(commentId);
    }

    @Override
    public void onError(Throwable e) {

    }
}
