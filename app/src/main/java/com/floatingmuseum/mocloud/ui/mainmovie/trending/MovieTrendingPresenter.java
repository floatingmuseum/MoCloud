package com.floatingmuseum.mocloud.ui.mainmovie.trending;


import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class MovieTrendingPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {

    private MovieTrendingFragment fragment;
    private int limit = 12;
    private int pageNum = 1;
    protected Boolean shouldClean;

    MovieTrendingPresenter(@NonNull MovieTrendingFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(final boolean shouldClean) {
        Logger.d("刷新...start:"+shouldClean);
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
//        repository.getMovieTrendingData1(pageNum,limit,this);
        repository.getMovieTrendingData(pageNum,limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> data) {
        fragment.refreshData(data,shouldClean);
        fragment.stopRefresh();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        fragment.stopRefresh();
        Logger.d("onError");
        e.printStackTrace();
    }
}
