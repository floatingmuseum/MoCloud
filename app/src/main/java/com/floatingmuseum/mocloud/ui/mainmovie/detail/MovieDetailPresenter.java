package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.People;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter implements MovieDetailCallback<Movie> {

    MovieDetailActivity activity;
    Repository repository;
    private int limit = 4;
    private int page = 1;

    @Inject
    MovieDetailPresenter(@NonNull MovieDetailActivity activity,@NonNull Repository repository){
        this.activity = activity;
        this.repository = repository;
    }

    public void getData(String movieId){
        repository.getMovieDetail(movieId,this);
        repository.getMoviePeople(movieId,this);
        repository.getMovieComments(movieId,Repository.COMMENTS_SORT_LIKES,limit,page,this,null);
    }

    @Override
    public void onBaseDataSuccess(Movie movie) {
        activity.onBaseDataSuccess(movie);
    }

    @Override
    public void onPeopleSuccess(People people) {
        activity.onPeopleSuccess(people);
    }

    @Override
    public void onCommentsSuccess(List<Comment> comments) {
        activity.onCommentsSuccess(comments);
    }

    @Override
    public void onError(Throwable e) {

    }
}
