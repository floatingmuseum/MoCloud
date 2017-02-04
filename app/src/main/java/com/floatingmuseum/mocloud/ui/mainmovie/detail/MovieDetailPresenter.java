package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
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
public class MovieDetailPresenter extends Presenter implements MovieDetailCallback{

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
        compositeSubscription.add(repository.getMovieImdbRatings(movie.getIds().getImdb(), this));
    }

//    public void getData(TmdbMovieDetail movie) {
//        compositeSubscription.add(repository.getMovieDetail(movie.getId(), this));
//        if (movie.isTraktItem()) {
//            isTraktItem = movie.isTraktItem();
//            getImdbRatings(movie.getImdb_id());
//        }
//    }

    @Override
    public void onMovieTeamSuccess(MovieTeam movieTeam) {
        activity.onMovieTeamSuccess(movieTeam);
    }

    public void getTraktRatings(String imdbId) {
        compositeSubscription.add(repository.getMovieTraktRatings(imdbId, this));
    }

    public void getImdbRatings(String imdbId) {
        compositeSubscription.add(repository.getMovieImdbRatings(imdbId, this));
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
    public void onBaseDataSuccess(Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
