package com.floatingmuseum.mocloud.ui.movie.collected;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieCollectedPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {
    private MovieCollectedFragment fragment;
    private String period = "weekly";

    public MovieCollectedPresenter(@NonNull MovieCollectedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        repository.getMovieCollectedData(period,getPageNum(shouldClean),limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        fragment.refreshData(baseMovies,shouldClean);
        fragment.stopRefresh();
        if (!shouldClean){
            pageNum+=1;
        }
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
