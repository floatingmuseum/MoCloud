package com.floatingmuseum.mocloud.ui.mainmovie.collected;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieCollectedPresenter implements MovieCollectedContract.Presenter, DataCallback<List<BaseMovie>> {
    private MovieCollectedContract.View collectedView;
    private Repository repository;
    private int pageNum = 1;
    private int limit = 12;
    private String period = "weekly";
    protected Boolean shouldClean;

    public MovieCollectedPresenter(@NonNull MovieCollectedContract.View collectedView,@NonNull Repository repository){
        this.collectedView = collectedView;
        this.repository = repository;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieCollectedData(period,pageNum,limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        collectedView.refreshData(baseMovies,shouldClean);
        collectedView.stopRefresh();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        collectedView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
