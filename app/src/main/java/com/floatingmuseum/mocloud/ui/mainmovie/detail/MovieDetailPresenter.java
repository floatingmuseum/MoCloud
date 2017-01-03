package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;

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


    MovieDetailPresenter(@NonNull MovieDetailActivity activity){
        this.activity = activity;
    }

    public void getData(int tmdbId){
        movieDetailSubscription = repository.getMovieDetail(tmdbId,this);
        compositeSubscription.add(movieDetailSubscription);
        movieTeamSubscription = repository.getMovieTeam(tmdbId,this);
        compositeSubscription.add(movieTeamSubscription);
    }

    @Override
    public void onBaseDataSuccess(TmdbMovieDetail movie) {
        activity.onBaseDataSuccess(movie);
        movieCommentsSubscription = repository.getMovieComments(movie.getImdb_id(), Repository.COMMENTS_SORT_LIKES,limit,page,this,null);
        compositeSubscription.add(movieCommentsSubscription);
    }

    @Override
    public void onPeopleSuccess(TmdbPeople staffs){
        activity.onPeopleSuccess(staffs);
    }

    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        activity.onCommentsSuccess(comments);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void start(boolean shouldClean) {

    }
}
