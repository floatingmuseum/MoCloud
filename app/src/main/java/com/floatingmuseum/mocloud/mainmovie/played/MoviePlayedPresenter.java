package com.floatingmuseum.mocloud.mainmovie.played;


import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.date.Repository;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedPresenter implements MoviePlayedContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>> {

    private MoviePlayedContract.View playedView;
    private Repository repository;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    @Inject
    public MoviePlayedPresenter(MoviePlayedContract.View playedView, Repository repository){
        this.playedView = playedView;
        this.repository = repository;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMoviePlayedData(period,pageNum,this);
    }

    @Override
    public void onDestroy() {
        repository.destroyCompositeSubscription();
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        playedView.refreshData(baseMovies,shouldClean);
        playedView.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        playedView.stopRefresh();
    }
}
