package com.floatingmuseum.mocloud.ui.comments;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.MovieCommentsCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/9/2.
 */
public class CommentsPresenter implements CommentsContract.Presenter,MovieCommentsCallback<List<Comment>> {

    CommentsActivity activity;
    Repository repository;
    private int limit = 20;
    private int page = 1;

    @Inject
    public CommentsPresenter(@NonNull CommentsActivity activity,@NonNull Repository repository){
        this.activity = activity;
        this.repository = repository;
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
}
