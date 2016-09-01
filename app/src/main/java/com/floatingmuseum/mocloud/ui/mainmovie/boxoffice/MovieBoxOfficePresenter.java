package com.floatingmuseum.mocloud.ui.mainmovie.boxoffice;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficePresenter implements MovieBoxOfficeContract.Presenter, DataCallback<List<BaseMovie>> {

    private MovieBoxOfficeContract.View boxOfficeView;
    private Repository repository;
    protected Boolean shouldClean;

    @Inject
    public MovieBoxOfficePresenter(@NonNull MovieBoxOfficeContract.View boxOfficeView,@NonNull Repository repository){
        this.boxOfficeView = boxOfficeView;
        this.repository = repository;
    }

    @Override
    public void start() {
        repository.getMovieBoxOfficeData(this);
    }

    @Override
    public void onSuccess(List<BaseMovie> baseMovies) {
        boxOfficeView.refreshData(baseMovies);
        boxOfficeView.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        boxOfficeView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
