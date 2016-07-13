package com.floatingmuseum.mocloud.mainmovie.watched;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.date.Repository;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedPresenter implements MovieWatchedContract.Presenter,Repository.DataCallback<List<BaseMovie>>{
    private MovieWatchedContract.View watchedView;
    private int pageNum = 1;
    private String period = "weekly";
    private Repository repository;
    protected Boolean shouldClean;

    @Inject
    public MovieWatchedPresenter(MovieWatchedContract.View watchedView, Repository repository){
        this.repository = repository;
        this.watchedView = watchedView;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieWatchedData(period,pageNum,this);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        watchedView.refreshData(baseMovies,shouldClean);
        watchedView.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        watchedView.stopRefresh();
    }
}
