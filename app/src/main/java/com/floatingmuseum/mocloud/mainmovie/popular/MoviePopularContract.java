package com.floatingmuseum.mocloud.mainmovie.popular;

import com.floatingmuseum.mocloud.base.BasePresenter;
import com.floatingmuseum.mocloud.base.BaseView;
import com.floatingmuseum.mocloud.data.entity.Movie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularContract {
    public interface View extends BaseView<Presenter> {
        void refreshData(List<Movie> newData, boolean shouldClean);
    }

    interface Presenter extends BasePresenter {
        void onDestroy();
    }
}
