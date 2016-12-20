package com.floatingmuseum.mocloud.ui.comments;

import android.app.Activity;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/20.
 */

public class SingleCommentPresenter extends Presenter implements DataCallback<List<Comment>> {

    private SingleCommentActivity activity;
    private Repository repository;

    public SingleCommentPresenter(SingleCommentActivity activity, Repository repository){
        this.activity = activity;
        this.repository = repository;
    }

    public void getData(long commentId){
        repository.getCommentReplies(commentId,this);
    }

    @Override
    public void onBaseDataSuccess(List<Comment> replies) {
        activity.onBaseDataSuccess(replies);
    }

    @Override
    public void onError(Throwable e) {

    }
}
