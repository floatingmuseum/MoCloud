package com.floatingmuseum.mocloud.mainmovie.trending;


import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class MovieTrendingPresenter implements MovieTrendingContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>> {

    private MovieTrendingContract.View mTrendingView;
    private MovieTrendingRepo repo;
    private int pageNum = 1;
    protected Boolean shouldClean;

    public MovieTrendingPresenter(MovieTrendingContract.View trendingView){
        mTrendingView = trendingView;
        mTrendingView.setPresenter(this);
        repo = new MovieTrendingRepo(this);
    }

    @Override
    public void start(final boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repo.getData(pageNum);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSuccess(List<BaseMovie> t) {
        mTrendingView.refreshData(t,shouldClean);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mTrendingView.stopRefresh();
    }
}
