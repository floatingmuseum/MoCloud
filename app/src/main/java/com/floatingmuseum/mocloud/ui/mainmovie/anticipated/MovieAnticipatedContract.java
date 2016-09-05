package com.floatingmuseum.mocloud.ui.mainmovie.anticipated;

import com.floatingmuseum.mocloud.base.BasePresenter;
import com.floatingmuseum.mocloud.base.BaseView;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieAnticipatedContract {
    public interface View extends BaseView<Presenter> {
        void refreshData(List<BaseMovie> newData, boolean shouldClean);
    }

    interface Presenter extends BasePresenter {
        void onDestroy();
    }
}