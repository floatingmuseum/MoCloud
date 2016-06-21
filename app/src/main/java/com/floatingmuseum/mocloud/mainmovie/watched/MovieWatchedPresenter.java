package com.floatingmuseum.mocloud.mainmovie.watched;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedPresenter implements MovieWatchedContract.Presenter,BaseRepo.DataCallback<List<BaseMovie>>{
    private MovieWatchedContract.View mWatchedView;
    private MovieWatchedRepo repo;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    @Inject
    public MovieWatchedPresenter(MovieWatchedContract.View WatchedView){
        mWatchedView = WatchedView;
        repo = new MovieWatchedRepo(this);
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repo.getData(period,pageNum);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        mWatchedView.refreshData(baseMovies,shouldClean);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mWatchedView.stopRefresh();
    }
}
