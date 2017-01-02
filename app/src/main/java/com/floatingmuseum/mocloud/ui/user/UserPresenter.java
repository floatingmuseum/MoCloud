package com.floatingmuseum.mocloud.ui.user;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.UserDetailCallback;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.Stats;
import com.floatingmuseum.mocloud.data.entity.User;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/28.
 */

public class UserPresenter extends Presenter implements UserDetailCallback{
    private UserActivity activity;

    public UserPresenter(UserActivity activity){
        this.activity = activity;
    }

    public void start(String slug) {
        compositeSubscription.add(repository.getUserStats(slug,this));
        compositeSubscription.add(repository.getUserFollowers(slug,this));
        compositeSubscription.add(repository.getUserFollowing(slug,this));
    }

    @Override
    public void onUserFollowersSuccess(List<Follower> followers) {
        activity.onUserFollowersSuccess(followers);
    }

    @Override
    public void onUserFollowingSuccess(List<Follower> followers) {
        activity.onUserFollowingSuccess(followers);
    }

    @Override
    public void onUserStatsSuccess(Stats stats) {
        activity.onUserStatsSuccess(stats);
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void start(boolean shouldClean) {

    }
}
