package com.floatingmuseum.mocloud.ui.mainmovie.nowplaying;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;

/**
 * Created by Floatingmuseum on 2017/1/3.
 */

public class MovieNowPlayingPresenter extends Presenter implements DataCallback<TmdbMovieDataList> {

    private MovieNowPlayingFragment fragment;
    private int pageNum = 1;
    protected boolean shouldClean;

    public MovieNowPlayingPresenter(MovieNowPlayingFragment fragment){
        this.fragment = fragment;
    }


    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieNowPlaying(pageNum,this);
    }

    @Override
    public void onBaseDataSuccess(TmdbMovieDataList data) {
        fragment.refreshData(data,shouldClean);
        fragment.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        fragment.stopRefresh();
    }
}
