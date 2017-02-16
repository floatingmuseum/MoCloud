package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/2/16.
 */

public interface SyncCallback {

    void onError(Throwable e);
    void onSyncMovieHistorySucceed(List<MovieWatchedItem> movieWatchedItems);
}
