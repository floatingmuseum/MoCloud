package com.floatingmuseum.mocloud.ui.recommendations;

import android.app.Activity;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.RecommendationsCallback;
import com.floatingmuseum.mocloud.data.entity.Movie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/16.
 */

public class RecommendationsPresenter extends Presenter implements RecommendationsCallback<List<Movie>> {
    private RecommendationsActivity activity;

    public RecommendationsPresenter(RecommendationsActivity recommendationsActivity) {
        activity = recommendationsActivity;
    }

    public void getData() {
        compositeSubscription.add(repository.getRecommendations(this));
    }

    public void hideMovie(String slug) {
        compositeSubscription.add(repository.hideMovie(slug,this));
    }

    @Override
    public void onBaseDataSuccess(List<Movie> movies) {
        activity.onBaseDataSuccess(movies);
    }

    @Override
    public void onHideMovieSuccess() {
        activity.onHideMovieSuccess();
    }

    @Override
    public void onError(Throwable e) {

    }
}
