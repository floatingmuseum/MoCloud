package com.floatingmuseum.mocloud.ui.trakt_mainmovie.boxoffice;

import com.floatingmuseum.mocloud.base.BasePresenter;
import com.floatingmuseum.mocloud.base.BaseView;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficeContract {
    public interface View extends BaseView<Presenter> {
        void refreshData(List<BaseMovie> newData);
    }

    interface Presenter extends BasePresenter{
        void onDestroy();
    }
}
