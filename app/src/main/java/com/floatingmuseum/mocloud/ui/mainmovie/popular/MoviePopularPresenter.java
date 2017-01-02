package com.floatingmuseum.mocloud.ui.mainmovie.popular;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public class MoviePopularPresenter extends Presenter implements DataCallback<TmdbMovieDataList>{
    private MoviePopularFragment fragment;
    private int pageNum = 1;
    protected boolean shouldClean;


    public MoviePopularPresenter(MoviePopularFragment fragment){
        this.fragment = fragment;
    }

    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMoviePopular(pageNum,this);
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
