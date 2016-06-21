package com.floatingmuseum.mocloud.mainmovie.anticipated;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieAnticipatedPresenter implements MovieAnticipatedContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>>{

    private MovieAnticipatedContract.View mAnticipatedView;
    private MovieAnticipatedRepo repo;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    @Inject
    public MovieAnticipatedPresenter(MovieAnticipatedContract.View AnticipatedView){
        mAnticipatedView = AnticipatedView;
        repo = new MovieAnticipatedRepo(this);
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repo.getData(period,pageNum);
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        mAnticipatedView.refreshData(baseMovies,shouldClean);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mAnticipatedView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
