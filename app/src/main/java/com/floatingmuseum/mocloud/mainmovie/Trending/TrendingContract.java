package com.floatingmuseum.mocloud.mainmovie.Trending;

import com.floatingmuseum.mocloud.base.BasePresenter;
import com.floatingmuseum.mocloud.base.BaseView;
import com.floatingmuseum.mocloud.model.entity.Image;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.entity.Trending;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public interface TrendingContract {
    interface View extends BaseView<Presenter>{
        void refreshData(List<Trending> newData,List<Image> images);
        TrendingAdapter getTrendingAdapter();
    }

    interface Presenter extends BasePresenter{
        void onDestroy();
    }
}
