package com.floatingmuseum.mocloud.ui.mainmovie.watched;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedPresenter extends Presenter implements MovieWatchedContract.Presenter,DataCallback<List<BaseMovie>> {
    private MovieWatchedContract.View watchedView;
    private int pageNum = 1;
    private int limit = 12;
    private String period = "weekly";
    protected Boolean shouldClean;

    public MovieWatchedPresenter(MovieWatchedContract.View watchedView){
        this.watchedView = watchedView;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieWatchedData(period,pageNum,limit,this);
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        watchedView.refreshData(baseMovies,shouldClean);
        watchedView.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        watchedView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
