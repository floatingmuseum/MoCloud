package com.floatingmuseum.mocloud.ui.recommendations;


import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.RecommendationsCallback;
import com.floatingmuseum.mocloud.data.entity.FeatureList;
import com.floatingmuseum.mocloud.data.entity.FeatureListItem;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by Floatingmuseum on 2017/1/16.
 */

public class RecommendationsPresenter extends Presenter implements RecommendationsCallback<List<Movie>> {
    private RecommendationsActivity activity;

    RecommendationsPresenter(RecommendationsActivity recommendationsActivity) {
        activity = recommendationsActivity;
    }

    void getData(Map<String, String> featureList) {
        compositeSubscription.add(repository.getRecommendations(this));
        for (String listID : featureList.keySet()) {
            compositeSubscription.add(repository.getFeatureList(featureList.get(listID), listID, this));
            compositeSubscription.add(repository.getFeatureListData(featureList.get(listID), listID, this));
        }
    }

    public void hideMovie(String slug) {
        compositeSubscription.add(repository.hideMovie(slug, this));
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
    public void onGetFeatureListSuccess(FeatureList featureList) {
        activity.onGetFeatureListSuccess(featureList);
    }

    @Override
    public void onGetFeatureListDataSuccess(String listID, List<FeatureListItem> data) {
        activity.onGetFeatureListDataSuccess(listID,data);
        Logger.d("特色List...onGetFeatureListDataSuccess:" + listID + "..." + data.size());
    }

    @Override
    public void onError(Throwable e) {

    }
}
