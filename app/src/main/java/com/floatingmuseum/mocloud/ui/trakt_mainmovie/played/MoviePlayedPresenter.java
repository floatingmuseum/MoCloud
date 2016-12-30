package com.floatingmuseum.mocloud.ui.trakt_mainmovie.played;


import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedPresenter extends Presenter implements MoviePlayedContract.Presenter, DataCallback<List<BaseMovie>> {

    private MoviePlayedContract.View playedView;
    private int limit = 12;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    public MoviePlayedPresenter(MoviePlayedContract.View playedView){
        this.playedView = playedView;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMoviePlayedData(period,pageNum,limit,this);
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        playedView.refreshData(baseMovies,shouldClean);
        playedView.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        playedView.stopRefresh();
    }

    @Override
    public void onDestroy() {
    }
}
