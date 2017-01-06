package com.floatingmuseum.mocloud.ui.mainmovie.toprated;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;

/**
 * Created by Floatingmuseum on 2017/1/3.
 */

public class MovieTopRatedPresenter extends ListPresenter implements DataCallback<TmdbMovieDataList>{

    private MovieTopRatedFragment fragment;
    private int pageNum = 1;
    protected boolean shouldClean;

    public MovieTopRatedPresenter(MovieTopRatedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieTopRated(pageNum,this);
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
