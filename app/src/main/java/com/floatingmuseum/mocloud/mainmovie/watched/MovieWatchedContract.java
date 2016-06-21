package com.floatingmuseum.mocloud.mainmovie.watched;


import com.floatingmuseum.mocloud.base.BasePresenter;
import com.floatingmuseum.mocloud.base.BaseView;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedContract {
    public interface View extends BaseView<Presenter> {
        void refreshData(List<BaseMovie> newData, boolean shouldClean);
    }

    interface Presenter extends BasePresenter {
        void onDestroy();
    }
}
