package com.floatingmuseum.mocloud.ui.movie.trending;


import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.repo.MovieRepository;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
class MovieTrendingPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {

    private MovieTrendingFragment fragment;

    MovieTrendingPresenter(@NonNull MovieTrendingFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void start(final boolean shouldClean) {
        Logger.d("刷新...start:" + shouldClean);
        MovieRepository.getInstance().getMovieTrendingData(getPageNum(shouldClean), limit, this);
//        repository.getMovieTrendingData(getPageNum(shouldClean), limit, this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> data) {
        fragment.refreshData(data, shouldClean);
        //请求成功后，页码永久+1
        if (!shouldClean) {
            pageNum += 1;
        }
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        fragment.onError();
        Logger.d("onError");
        e.printStackTrace();
    }
}
