package com.floatingmuseum.mocloud.ui.comments;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/9/2.
 */
public class CommentsPresenter extends Presenter implements CommentsContract.Presenter,CommentsCallback<List<Comment>> {

    CommentsActivity activity;
    private int limit = 20;
    private int page = 1;

    public CommentsPresenter(@NonNull CommentsActivity activity){
        this.activity = activity;
    }

    @Override
    public void start(String movieId,boolean shouldClean) {
        page = shouldClean?1:++page;
        repository.getMovieComments(movieId,Repository.COMMENTS_SORT_NEWEST,limit,page,null,this);
    }

    @Override
    public void onBaseDataSuccess(List<Comment> comments) {
        activity.onBaseDataSuccess(comments);
        activity.stopRefresh();
    }

    public void sendComment(Comment comment) {
        repository.sendComment(comment,this);
    }

    @Override
    public void onSendCommentSuccess(Comment comment) {
        activity.onSendCommentSuccess(comment);
    }

    @Override
    public void onError(Throwable e) {
        activity.stopRefresh();
        Logger.d("onError");
        e.printStackTrace();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void start(boolean shouldClean) {

    }
}
