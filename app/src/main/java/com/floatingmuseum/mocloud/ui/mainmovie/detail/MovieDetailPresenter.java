package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;

import java.util.List;

import rx.Subscription;


/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter extends Presenter implements MovieDetailCallback<TmdbMovieDetail> {

    MovieDetailActivity activity;
    private int limit = 4;
    private int page = 1;
    private Subscription movieDetailSubscription;
    private Subscription movieTeamSubscription;
    private Subscription movieCommentsSubscription;


    MovieDetailPresenter(@NonNull MovieDetailActivity activity) {
        this.activity = activity;
    }

    public void getData(int tmdbId) {
        movieDetailSubscription = repository.getMovieDetail(tmdbId, this);
        compositeSubscription.add(movieDetailSubscription);
    }

    @Override
    public void onBaseDataSuccess(TmdbMovieDetail movie) {
        movieCommentsSubscription = repository.getMovieComments(movie.getImdb_id(), CommentsActivity.SORT_BY_LIKES, limit, page, this, null);
        compositeSubscription.add(movieCommentsSubscription);
        getRatings(movie.getImdb_id());
        activity.onBaseDataSuccess(movie);
        activity.onPeopleSuccess(movie.getCredits());
    }

    public void getRatings(String imdbId) {
        repository.getMovieTraktRatings(imdbId, this);
        repository.getMovieImdbRatings(imdbId, this);
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
    public void onImdbRatingsSuccess(OmdbInfo omdbInfo) {
        activity.onImdbRatingsSuccess(omdbInfo);
    }

    public void sendComment(Comment comment, String imdb_id) {
        repository.sendComment(comment, imdb_id, this);
    }

    @Override
    public void onSendCommentSuccess(Comment comment) {
        activity.onSendCommentSuccess(comment);
    }

    @Override
    public void onError(Throwable e) {

    }
}
