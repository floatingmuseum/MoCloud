package com.floatingmuseum.mocloud.data.callback;

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
    void onLastActivitiesSucceed(LastActivities lastActivities);
    void onSyncUserSettingsSucceed(UserSettings userSettings);
    void onSyncMovieWatchedSucceed(List<MovieWatchedItem> movieWatchedItems);
    void onSyncMovieCollectionSucceed(List<MovieCollectionItem> movieCollectionItems);
    void onSyncMovieRatingsSucceed(List<MovieRatingItem> movieRatingItems);
    void onSyncMovieWatchlistSucceed(List<MovieWatchlistItem> movieWatchlistItems);
    void onSyncUserListLikes(List<UserListLike> userListLikes);
    void onSyncUserCommentsLikes(List<UserCommentLike> userCommentLikes);
}
