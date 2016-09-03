package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Floatingmuseum on 2016/9/2.
 */
@Module
public class CommentsPresenterModule {

    private CommentsActivity commentsActivity;

    public CommentsPresenterModule(CommentsActivity commentsActivity){
        this. commentsActivity = commentsActivity;
    }

    @Provides
    CommentsActivity providesCommentsActivity(){
        return commentsActivity;
    }
}
