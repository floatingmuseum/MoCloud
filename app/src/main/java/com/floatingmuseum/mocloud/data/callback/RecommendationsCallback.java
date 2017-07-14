package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.FeatureList;

/**
 * Created by Floatingmuseum on 2017/1/16.
 */

public interface RecommendationsCallback<T> extends DataCallback<T> {
    void onHideMovieSuccess();
    void onGetFeatureListSuccess(FeatureList featureList);
}
