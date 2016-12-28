package com.floatingmuseum.mocloud.ui.user;

import com.floatingmuseum.mocloud.base.Presenter;

/**
 * Created by Floatingmuseum on 2016/12/28.
 */

public class UserPresenter extends Presenter {
    private UserActivity activity;

    public UserPresenter(UserActivity activity){
        this.activity = activity;
    }

    public void start(String slug) {
        repository.getUserFollowers(slug);
        repository.getUserFollowing(slug);
        repository.getUserStats(slug);
    }
}
