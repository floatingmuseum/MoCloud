package com.floatingmuseum.mocloud.ui.mainmovie.popular;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Movie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularPresenter implements MoviePopularContract.Presenter,DataCallback<List<Movie>> {

    private MoviePopularContract.View popularView;
    private int pageNum = 1;
    private int limit = 12;
    protected Boolean shouldClean;
    private Repository repository;

    @Inject
    public MoviePopularPresenter(@NonNull MoviePopularContract.View popularView, @NonNull Repository repository){
        this.popularView = popularView;
        this.repository = repository;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMoviePopularData(pageNum,limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<Movie> movies) {
        popularView.refreshData(movies,shouldClean);
        popularView.stopRefresh();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        popularView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
