package com.floatingmuseum.mocloud.ui.mainmovie.boxoffice;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;


/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficePresenter extends Presenter implements MovieBoxOfficeContract.Presenter, DataCallback<List<BaseMovie>> {

    private MovieBoxOfficeContract.View boxOfficeView;
    protected boolean shouldClean;

    public MovieBoxOfficePresenter(@NonNull MovieBoxOfficeContract.View boxOfficeView){
        this.boxOfficeView = boxOfficeView;
    }

    @Override
    public void start(boolean shouldClean) {
        repository.getMovieBoxOfficeData(this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
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
