package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter implements MovieDetailCallback<Movie> {

    MovieDetailActivity activity;
    Repository repository;
    private int limit = 4;
    private int page = 1;
    private Subscription movieDetailSubscription;
    private Subscription movieTeamSubscription;
    private Subscription movieCommentsSubscription;
    private Subscription chuanchuan;


    MovieDetailPresenter(@NonNull MovieDetailActivity activity,@NonNull Repository repository){
        this.activity = activity;
        this.repository = repository;
    }

    public void getData(Movie movie){
//        CompositeSubscription compositeSubscription = new CompositeSubscription();
        chuanchuan = repository.getMovieTeam(movie.getIds().getSlug(),this);
        movieDetailSubscription = repository.getMovieDetail(movie.getIds().getSlug(),this);
        movieTeamSubscription = repository.getMovieTeam(movie.getIds().getTmdb(),this);
//        movieTeamSubscription = repository.getMovieTeam(movie.getIds().getSlug(),this);
        movieCommentsSubscription = repository.getMovieComments(movie.getIds().getSlug(), Repository.COMMENTS_SORT_LIKES,limit,page,this,null);
    }

    @Override
    public void onBaseDataSuccess(Movie movie) {
        activity.onBaseDataSuccess(movie);
    }

    @Override
    public void onPeopleSuccess(TmdbPeople people) {
        activity.onPeopleSuccess(people);
    }


    public void onPeopleSuccess(List<Staff> staffs){
        activity.onPeopleSuccess(staffs);
    }

    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        activity.onCommentsSuccess(comments);
    }

    @Override
    public void onError(Throwable e) {

    }

    public void unSubscription(){
        if (!movieDetailSubscription.isUnsubscribed()) {
            movieDetailSubscription.unsubscribe();
        }
        if (!movieTeamSubscription.isUnsubscribed()) {
            movieTeamSubscription.unsubscribe();
        }
        if (!movieCommentsSubscription.isUnsubscribed()) {
            movieCommentsSubscription.unsubscribe();
        }
        if (!chuanchuan.isUnsubscribed()) {
            chuanchuan.unsubscribe();
        }
    }
}
