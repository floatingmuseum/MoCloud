package com.floatingmuseum.mocloud.mainmovie.collected;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieCollectedPresenter implements MovieCollectedContract.Presenter, DataCallback<List<BaseMovie>> {
    private MovieCollectedContract.View collectedView;
    private Repository repository;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    @Inject
    public MovieCollectedPresenter(@NonNull MovieCollectedContract.View collectedView,@NonNull Repository repository){
        this.collectedView = collectedView;
        this.repository = repository;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieCollectedData(period,pageNum,this);
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        collectedView.refreshData(baseMovies,shouldClean);
        collectedView.stopRefresh();
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
