package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.dagger.ActivityScope;
import com.floatingmuseum.mocloud.dagger.repo.RepoComponent;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;

import dagger.Component;

/**
 * Created by Floatingmuseum on 2016/9/2.
 */
@ActivityScope
@Component(dependencies = RepoComponent.class,modules = CommentsPresenterModule.class)
public interface CommentsPresenterComponent {
    void inject(CommentsActivity commentsActivity);
}
