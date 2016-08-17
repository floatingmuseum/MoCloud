package com.floatingmuseum.mocloud.mainmovie.moviedetail;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.MovieDetail;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/7/14.
 */
public class MovieDetailPresenter implements MovieDetailCallback {

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
    public void onSuccess(MovieDetail movieDetail) {
        activity.onSuccess(movieDetail);
    }

    @Override
    public void onError(Throwable e) {

    }
}
