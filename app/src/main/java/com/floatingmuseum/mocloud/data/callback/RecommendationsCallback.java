package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.FeatureList;
import com.floatingmuseum.mocloud.data.entity.FeatureListItem;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/16.
 */

public interface RecommendationsCallback<T> extends DataCallback<T> {
    void onHideMovieSuccess();
    void onGetFeatureListSuccess(FeatureList featureList);
    void onGetFeatureListDataSuccess(String listID,List<FeatureListItem> data);
}
