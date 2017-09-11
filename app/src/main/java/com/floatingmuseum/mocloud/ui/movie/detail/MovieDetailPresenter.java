package com.floatingmuseum.mocloud.ui.movie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseCommentsPresenter;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.db.RealmManager;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieCollection;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieRating;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatched;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatchlist;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.SyncData;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.SyncResponse;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter extends BaseCommentsPresenter implements MovieDetailCallback<List<Comment>> {

    private MovieDetailActivity activity;
    private int limit = 4;
    private int page = 1;
    private boolean isTraktItem = false;

    MovieDetailPresenter(@NonNull MovieDetailActivity activity) {
        this.activity = activity;
    }

    void getData(Movie movie) {
        compositeSubscription.add(repository.getMovieTeam(movie.getIds().getSlug(), this));
        compositeSubscription.add(repository.getMovieComments(movie.getIds().getSlug(), CommentsActivity.SORT_BY_LIKES, limit, page, this, null));
        // TODO: 2017/9/11 获取Imdb等其他评分的接口暂时不能用 
//        compositeSubscription.add(repository.getMovieOtherRatings(movie.getIds().getImdb(), this));
    }

    void loadUserData(int traktID) {
        RealmMovieWatched realmMovieWatched = RealmManager.query(RealmMovieWatched.class, "trakt_id", traktID);
        if (realmMovieWatched != null) {
            activity.updateLoginView(R.id.fb_watched, realmMovieWatched);
            Logger.d("本地查询RealmMovieWatched:" + realmMovieWatched.getTitle() + "..." + realmMovieWatched.getLast_watched_at());
        }
        RealmMovieWatchlist realmMovieWatchlist = RealmManager.query(RealmMovieWatchlist.class, "trakt_id", traktID);
        if (realmMovieWatchlist != null) {
            activity.updateLoginView(R.id.fb_watchlist, realmMovieWatchlist);
            Logger.d("本地查询RealmMovieWatchlist:" + realmMovieWatchlist.getTitle() + "..." + realmMovieWatchlist.getListed_at());
        }
        RealmMovieCollection realmMovieCollection = RealmManager.query(RealmMovieCollection.class, "trakt_id", traktID);
        if (realmMovieCollection != null) {
            activity.updateLoginView(R.id.fb_collection, realmMovieCollection);
            Logger.d("本地查询RealmMovieCollection:" + realmMovieCollection.getTitle() + "..." + realmMovieCollection.getCollected_at());
        }
        RealmMovieRating realmMovieRating = RealmManager.query(RealmMovieRating.class, "trakt_id", traktID);
        if (realmMovieRating != null) {
//            activity.updateLoginView(R.id.,realmMovieRating);
            Logger.d("本地查询RealmMovieRating:" + realmMovieRating.getTitle() + "..." + realmMovieRating.getRated_at() + "..." + realmMovieRating.getRating());
        }
    }

    @Override
    public void onMovieTeamSuccess(MovieTeam movieTeam) {
        activity.onMovieTeamSuccess(movieTeam);
    }

    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        activity.onCommentsSuccess(comments);
    }

    @Override
    public void onTraktRatingsSuccess(Ratings ratings) {
        activity.onTraktRatingsSuccess(ratings);
    }

    @Override
    public void onOtherRatingsSuccess(OmdbInfo omdbInfo) {
        activity.onOtherRatingsSuccess(omdbInfo);
    }

    void sendComment(Comment comment, String imdb_id) {
        repository.sendComment(comment, imdb_id, this);
    }

    @Override
    public void onSendCommentSuccess(Comment comment) {
        activity.onSendCommentSuccess(comment);
    }

    void syncMovieWatchedState(boolean hasWatched, SyncData item) {
        Logger.d("看过测试:syncMovieWatchedState...是否看过:" + hasWatched);
        if (!hasWatched) {
            repository.addMovieToWatched(item, this);
        } else {
            repository.removeMovieFromWatched(item, this);
        }
    }

    void syncMovieWatchlistState(boolean hasWatchlist, SyncData item) {
        Logger.d("想看测试:syncMovieWatchlistState...是否想看:" + hasWatchlist);
        if (!hasWatchlist) {
            repository.addMovieToWatchlist(item, this);
        } else {
            repository.removeMovieFromWatchlist(item, this);
        }
    }

    void syncMovieCollectedState(boolean hasCollection, SyncData item) {
        Logger.d("收藏测试:syncMovieCollectedState...是否想看:" + hasCollection);
        if (!hasCollection) {
            repository.addMovieToCollection(item, this);
        } else {
            repository.removeMovieFromCollection(item, this);
        }
    }

//    public void syncCommentLike(boolean isLike, Comment comment) {
//        Logger.d("评论点赞测试:syncCommentLike...是否点赞:" + isLike);
//        if (isLike) {
//            repository.removeCommentFromLikes(comment, this);
//        } else {
//            repository.addCommentToLikes(comment, this);
//        }
//    }

    @Override
    public void onAddMovieToWatchedSuccess(SyncResponse syncResponse) {
        activity.onAddMovieToWatchedSuccess();
    }

    @Override
    public void onRemoveMovieFromWatchedSuccess(SyncResponse syncResponse) {
        activity.onRemoveMovieFromWatchedSuccess();
    }

    @Override
    public void onAddMovieToWatchlistSuccess(SyncResponse syncResponse) {
        activity.onAddMovieToWatchlistSuccess();
    }

    @Override
    public void onRemoveMovieFromWatchlistSuccess(SyncResponse syncResponse) {
        activity.onRemoveMovieFromWatchlistSuccess();
    }

    @Override
    public void onAddMovieToCollectionSuccess(SyncResponse syncResponse) {
        activity.onAddMovieToCollectionSuccess();
    }

    @Override
    public void onRemoveMovieFromCollectionSuccess(SyncResponse syncResponse) {
        activity.onRemoveMovieFromCollectionSuccess();
    }

    @Override
    public void onAddCommentToLikesSuccess(long commentId) {
        activity.onAddCommentToLikesSuccess(commentId);
    }

    @Override
    public void onRemoveCommentFromLikesSuccess(long commentId) {
        activity.onRemoveCommentFromLikesSuccess(commentId);
    }

    @Override
    public void onBaseDataSuccess(List<Comment> comments) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
