package com.floatingmuseum.mocloud.ui.mainmovie.collected;

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
public class MovieCollectedPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {
    private MovieCollectedFragment fragment;
    private int pageNum = 1;
    private int limit = 12;
    private String period = "weekly";
    protected Boolean shouldClean;

    public MovieCollectedPresenter(@NonNull MovieCollectedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
//        repository.getMovieCollectedData(period,pageNum,limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        fragment.refreshData(baseMovies,shouldClean);
        fragment.stopRefresh();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        fragment.stopRefresh();
    }
}
