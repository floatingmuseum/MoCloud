package com.floatingmuseum.mocloud.mainmovie.popular;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.date.Repository;
import com.floatingmuseum.mocloud.date.entity.Movie;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularPresenter implements MoviePopularContract.Presenter,Repository.DataCallback<List<Movie>> {

    private MoviePopularContract.View popularView;
    private int pageNum = 1;
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
        repository.getMoviePopularData(pageNum,this);
    }

    @Override
    public void onSuccess(List<Movie> movies) {
        popularView.refreshData(movies,shouldClean);
        popularView.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        popularView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
