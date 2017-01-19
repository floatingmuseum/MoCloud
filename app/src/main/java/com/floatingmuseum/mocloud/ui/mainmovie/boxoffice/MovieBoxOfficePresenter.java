package com.floatingmuseum.mocloud.ui.mainmovie.boxoffice;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;


/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficePresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {

    private MovieBoxOfficeFragment fragment;
    protected boolean shouldClean;

    public MovieBoxOfficePresenter(@NonNull MovieBoxOfficeFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
//        repository.getMovieBoxOfficeData(this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        fragment.refreshData(baseMovies);
        fragment.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        fragment.stopRefresh();
    }
}
