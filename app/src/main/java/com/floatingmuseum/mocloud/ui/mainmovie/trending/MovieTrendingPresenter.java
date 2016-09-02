package com.floatingmuseum.mocloud.ui.mainmovie.trending;


import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class MovieTrendingPresenter implements MovieTrendingContract.Presenter, DataCallback<List<BaseMovie>> {

    private MovieTrendingContract.View trendingView;
    private int limit = 12;
    private int pageNum = 1;
    protected Boolean shouldClean;
    private Repository repository;
    
    @Inject
    MovieTrendingPresenter(@NonNull MovieTrendingContract.View trendingView,@NonNull Repository repository){
        this.repository = repository;
        this.trendingView = trendingView;
    }

    @Override
    public void start(final boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieTrendingData(pageNum,limit,this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> t) {
        trendingView.refreshData(t,shouldClean);
        trendingView.stopRefresh();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        trendingView.stopRefresh();
        Logger.d("onError");
        e.printStackTrace();
    }
}
