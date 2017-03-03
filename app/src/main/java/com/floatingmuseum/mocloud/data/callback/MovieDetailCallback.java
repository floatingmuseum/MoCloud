package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.SyncResponse;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/8/15.
 */
public interface MovieDetailCallback extends CommentsCallback {
    void onMovieTeamSuccess(MovieTeam movieTeam);

    void onCommentsSuccess(List<Comment> comments);

    void onTraktRatingsSuccess(Ratings ratings);

    void onOtherRatingsSuccess(OmdbInfo omdbInfo);

    void onAddMovieToWatchedSucceed(SyncResponse syncResponse);

    void onRemoveMovieFromWatchedSucceed(SyncResponse syncResponse);

    void onAddMovieToWatchlistSucceed(SyncResponse syncResponse);

    void onRemoveMovieFromWatchlistSucceed(SyncResponse syncResponse);

    void onAddMovieToCollectionSucceed(SyncResponse syncResponse);

    void onRemoveMovieFromCollectionSucceed(SyncResponse syncResponse);
}
