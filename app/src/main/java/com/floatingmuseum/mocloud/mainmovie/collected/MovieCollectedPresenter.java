package com.floatingmuseum.mocloud.mainmovie.collected;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieCollectedPresenter implements MovieCollectedContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>>{

    private MovieCollectedContract.View mCollectedView;
    private MovieCollectedRepo repo;
    private int pageNum = 1;
    private String period = "weekly";
    protected Boolean shouldClean;

    public MovieCollectedPresenter(MovieCollectedContract.View CollectedView){
        mCollectedView = CollectedView;
        mCollectedView.setPresenter(this);
        repo = new MovieCollectedRepo(this);
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repo.getData(period,pageNum);
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        mCollectedView.refreshData(baseMovies,shouldClean);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mCollectedView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
