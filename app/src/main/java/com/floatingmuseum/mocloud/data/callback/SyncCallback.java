package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.LastActivities;
import com.floatingmuseum.mocloud.data.entity.MovieCollectionItem;
import com.floatingmuseum.mocloud.data.entity.MovieRatingItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchlistItem;
import com.floatingmuseum.mocloud.data.entity.UserCommentLike;
import com.floatingmuseum.mocloud.data.entity.UserListLike;
import com.floatingmuseum.mocloud.data.entity.UserSettings;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/2/16.
 */

public interface SyncCallback {

    void onError(Throwable e);

    void onLastActivitiesSuccess(LastActivities lastActivities);

    void onSyncUserSettingsSuccess(UserSettings userSettings);

    void onSyncMovieWatchedSuccess(List<MovieWatchedItem> movieWatchedItems);

    void onSyncMovieCollectionSuccess(List<MovieCollectionItem> movieCollectionItems);

    void onSyncMovieRatingsSuccess(List<MovieRatingItem> movieRatingItems);

    void onSyncMovieWatchlistSuccess(List<MovieWatchlistItem> movieWatchlistItems);

    void onSyncUserListLikesSuccess(List<UserListLike> userListLikes);

    void onSyncUserCommentsLikesSuccess(List<UserCommentLike> userCommentLikes);

    void onSyncUserFollowingSuccess(List<Follower> followingList);

    void onSyncUserFollowersSuccess(List<Follower> followers);
}
