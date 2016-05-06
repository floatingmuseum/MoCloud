package com.floatingmuseum.mocloud.mainmovie.played;


import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedPresenter implements MoviePlayedContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>> {

    private MoviePlayedContract.View mPlayedView;
    private MoviePlayedRepo repo;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    public MoviePlayedPresenter(MoviePlayedContract.View playedView){
        mPlayedView = playedView;
        mPlayedView.setPresenter(this);
        repo = new MoviePlayedRepo(this);
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
        mPlayedView.refreshData(baseMovies,shouldClean);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mPlayedView.stopRefresh();
    }
}
