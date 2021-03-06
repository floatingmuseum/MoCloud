package com.floatingmuseum.mocloud.ui.movie.watched;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.repo.MovieRepository;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {
    private MovieWatchedFragment fragment;
    private String period = "weekly";
    protected boolean shouldClean;

    public MovieWatchedPresenter(MovieWatchedFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        MovieRepository.getInstance().getMovieWatchedData(period, getPageNum(shouldClean), limit, this);
//        repository.getMovieWatchedData(period,getPageNum(shouldClean),limit,this);
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        fragment.refreshData(baseMovies, shouldClean);
        fragment.stopRefresh();
        if (!shouldClean) {
            pageNum += 1;
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        fragment.stopRefresh();
    }
}
