package com.floatingmuseum.mocloud.ui.mainmovie.watched;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {
    private MovieWatchedFragment fragment;
    private int pageNum = 1;
    private int limit = 12;
    private String period = "weekly";
    protected Boolean shouldClean;

    public MovieWatchedPresenter(MovieWatchedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
//        repository.getMovieWatchedData(period,pageNum,limit,this);
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        fragment.refreshData(baseMovies,shouldClean);
        fragment.stopRefresh();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        fragment.stopRefresh();
    }
}
