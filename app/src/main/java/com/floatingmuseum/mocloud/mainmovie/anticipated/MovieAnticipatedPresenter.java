package com.floatingmuseum.mocloud.mainmovie.anticipated;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.date.Repository;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieAnticipatedPresenter implements MovieAnticipatedContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>>{

    private MovieAnticipatedContract.View anticipatedView;
    private Repository repository;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    @Inject
    public MovieAnticipatedPresenter(@NonNull MovieAnticipatedContract.View anticipatedView, @NonNull Repository repository){
        this.anticipatedView = anticipatedView;
        this.repository = repository;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieAnticipatedData(period,pageNum,this);
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        anticipatedView.refreshData(baseMovies,shouldClean);
        anticipatedView.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        anticipatedView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
