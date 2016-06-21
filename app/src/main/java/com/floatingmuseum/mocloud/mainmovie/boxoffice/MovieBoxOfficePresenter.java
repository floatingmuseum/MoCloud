package com.floatingmuseum.mocloud.mainmovie.boxoffice;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficePresenter implements MovieBoxOfficeContract.Presenter, BaseRepo.DataCallback<List<BaseMovie>> {

    private MovieBoxOfficeContract.View mBoxOfficeView;
    private MovieBoxOfficeRepo repo;
    protected Boolean shouldClean;

    @Inject
    public MovieBoxOfficePresenter(MovieBoxOfficeContract.View BoxOfficeView){
        mBoxOfficeView = BoxOfficeView;
        repo = new MovieBoxOfficeRepo(this);
    }

    @Override
    public void start() {
        repo.getData();
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        mBoxOfficeView.refreshData(baseMovies);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        mBoxOfficeView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
