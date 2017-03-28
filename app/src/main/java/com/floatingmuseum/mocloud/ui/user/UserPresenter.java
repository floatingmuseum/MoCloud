package com.floatingmuseum.mocloud.ui.user;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.UserDetailCallback;
import com.floatingmuseum.mocloud.data.db.RealmManager;
import com.floatingmuseum.mocloud.data.db.entity.RealmFollower;
import com.floatingmuseum.mocloud.data.db.entity.RealmFollowing;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.Stats;

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
        getUserFollowData(slug);
//        compositeSubscription.add(repository.getUserFollowers(slug,this));
//        compositeSubscription.add(repository.getUserFollowing(slug,this));
    }

    public void getUserFollowData(String slug){
        RealmFollower realmFollower = RealmManager.query(RealmFollower.class,"slug",slug);
        RealmFollowing realmFollowing = RealmManager.query(RealmFollowing.class,"slug",slug);

        activity.onUserFollowDataSuccess(realmFollower,realmFollowing);
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
}
