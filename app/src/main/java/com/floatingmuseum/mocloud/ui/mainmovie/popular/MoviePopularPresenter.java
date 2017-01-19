package com.floatingmuseum.mocloud.ui.mainmovie.popular;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularPresenter extends ListPresenter implements DataCallback<List<Movie>> {

    private MoviePopularFragment fragment;
    private int pageNum = 1;
    private int limit = 12;
    protected Boolean shouldClean;


    public MoviePopularPresenter(@NonNull MoviePopularFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMoviePopularData(pageNum,limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<Movie> movies) {
        fragment.refreshData(movies,shouldClean);
        fragment.stopRefresh();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        fragment.stopRefresh();
    }
}
