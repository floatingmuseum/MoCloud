package com.floatingmuseum.mocloud.mainmovie.played;


import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedPresenter implements MoviePlayedContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>> {

    private MoviePlayedContract.View mPlayedView;
    private MoviePlayedRepo repo;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    @Inject
    public MoviePlayedPresenter(MoviePlayedContract.View playedView){
        mPlayedView = playedView;
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
        repo.destroyCompositeSubscription();
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        Logger.d("onSuccess"+mPlayedView);
        mPlayedView.refreshData(baseMovies,shouldClean);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mPlayedView.stopRefresh();
    }
}
