package com.floatingmuseum.mocloud.mainmovie.moviedetail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.Repository.DataCallback;
import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter implements DataCallback<Movie> {

    MovieDetailActivity activity;
    Repository repository;

    @Inject
    MovieDetailPresenter(@NonNull MovieDetailActivity activity,@NonNull Repository repository){
        this.activity = activity;
        this.repository = repository;
    }

    public void getData(String movieId){
        repository.getMovieDetail(movieId,this);
    }

    @Override
    public void onSuccess(Movie movie) {
        activity.success(movie);
    }

    @Override
    public void onError(Throwable e) {

    }
}
