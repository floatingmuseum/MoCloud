package com.floatingmuseum.mocloud.ui.mainmovie.upcoming;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;

/**
 * Created by Floatingmuseum on 2017/1/3.
 */

public class MovieUpcomingPresenter extends Presenter implements DataCallback<TmdbMovieDataList>{

    private MovieUpcomingFragment fragment;
    private int pageNum = 1;
    protected boolean shouldClean;

    public MovieUpcomingPresenter(MovieUpcomingFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieUpcoming(pageNum,this);
    }

    @Override
    public void onBaseDataSuccess(TmdbMovieDataList tmdbMovieDataList) {
        fragment.refreshData(tmdbMovieDataList,shouldClean);
        fragment.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        fragment.stopRefresh();
    }
}
