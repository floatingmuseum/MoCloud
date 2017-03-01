package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.db.RealmManager;
import com.floatingmuseum.mocloud.data.db.entity.RealmCommentLike;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieCollection;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieRating;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatched;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatchlist;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;
import com.orhanobut.logger.Logger;

import java.util.List;


/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter extends Presenter implements MovieDetailCallback {

    MovieDetailActivity activity;
    private int limit = 4;
    private int page = 1;
    private boolean isTraktItem = false;

    MovieDetailPresenter(@NonNull MovieDetailActivity activity) {
        this.activity = activity;
    }

    public void getData(Movie movie) {
        compositeSubscription.add(repository.getMovieTeam(movie.getIds().getSlug(), this));
        compositeSubscription.add(repository.getMovieComments(movie.getIds().getSlug(), CommentsActivity.SORT_BY_LIKES, limit, page, this, null));
        compositeSubscription.add(repository.getMovieOtherRatings(movie.getIds().getImdb(), this));
    }

    public void loadUserData(int traktId) {
        RealmMovieWatched realmMovieWatched = RealmManager.query(RealmMovieWatched.class, traktId);
        if (realmMovieWatched != null) {
            Logger.d("本地查询RealmMovieWatched:" + realmMovieWatched.getTitle() + "..." + realmMovieWatched.getLast_watched_at());
        }
        RealmMovieWatchlist realmMovieWatchlist = RealmManager.query(RealmMovieWatchlist.class, traktId);
        if (realmMovieWatchlist != null) {
            Logger.d("本地查询RealmMovieWatchlist:" + realmMovieWatchlist.getTitle() + "..." + realmMovieWatchlist.getListed_at());
        }
        RealmMovieCollection realmMovieCollection = RealmManager.query(RealmMovieCollection.class, traktId);
        if (realmMovieCollection != null) {
            Logger.d("本地查询RealmMovieCollection:" + realmMovieCollection.getTitle() + "..." + realmMovieCollection.getCollected_at());
        }
        RealmMovieRating realmMovieRating = RealmManager.query(RealmMovieRating.class, traktId);
        if (realmMovieWatchlist != null) {
            Logger.d("本地查询RealmMovieRating:" + realmMovieRating.getTitle() + "..." + realmMovieRating.getRated_at() + "..." + realmMovieRating.getRating());
        }
//        RealmCommentLike realmCommentLike = RealmManager.query(RealmCommentLike.class,traktId);
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

    public void sendComment(Comment comment, String imdb_id) {
        repository.sendComment(comment, imdb_id, this);
    }

    @Override
    public void onSendCommentSuccess(Comment comment) {
        activity.onSendCommentSuccess(comment);
    }

    @Override
    public void onBaseDataSuccess(Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
