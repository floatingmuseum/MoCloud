package com.floatingmuseum.mocloud.ui.trakt_mainmovie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;

import java.util.List;

import rx.Subscription;


/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter extends Presenter implements MovieDetailCallback<Movie> {

    MovieDetailActivity activity;
    private int limit = 4;
    private int page = 1;
    private Subscription movieDetailSubscription;
    private Subscription movieTeamSubscription;
    private Subscription movieCommentsSubscription;
//    private CompositeSubscription compositeSubscription;


    MovieDetailPresenter(@NonNull MovieDetailActivity activity){
        this.activity = activity;
    }

    public void getData(Movie movie){
//        compositeSubscription = new CompositeSubscription();
        repository.getMovieDetail(movie.getIds().getTmdb());
        movieTeamSubscription = repository.getMovieTeam(movie.getIds().getSlug(),this);
        compositeSubscription.add(movieTeamSubscription);
        movieDetailSubscription = repository.getMovieDetail(movie.getIds().getSlug(),this);
        compositeSubscription.add(movieDetailSubscription);
        movieCommentsSubscription = repository.getMovieComments(movie.getIds().getSlug(), Repository.COMMENTS_SORT_LIKES,limit,page,this,null);
        compositeSubscription.add(movieCommentsSubscription);
    }

    public void getPoster(int tmdb) {
        repository.getMoviePoster(tmdb,this);
    }

    @Override
    public void onBaseDataSuccess(Movie movie) {
        activity.onBaseDataSuccess(movie);
    }

    @Override
    public void onPeopleSuccess(List<Staff> staffs){
        activity.onPeopleSuccess(staffs);
    }

    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        activity.onCommentsSuccess(comments);
    }

    @Override
    public void onPosterSuccess(TmdbMovieImage tmdbMovieImage) {
        activity.onPosterSuccess(tmdbMovieImage);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void start(boolean shouldClean) {

    }

//    public void unSubscription(){
//        compositeSubscription.unsubscribe();
//    }
}
