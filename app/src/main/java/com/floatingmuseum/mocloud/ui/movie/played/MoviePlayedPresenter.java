package com.floatingmuseum.mocloud.ui.movie.played;


import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.repo.MovieRepository;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedPresenter extends ListPresenter implements  DataCallback<List<BaseMovie>> {

    private MoviePlayedFragment fragment;
    private String period = "weekly";

    public MoviePlayedPresenter(MoviePlayedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        MovieRepository.getInstance().getMoviePlayedData(period,getPageNum(shouldClean),limit,this);
//        repository.getMoviePlayedData(period,getPageNum(shouldClean),limit,this);
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        fragment.refreshData(baseMovies,shouldClean);
        fragment.stopRefresh();
        if (!shouldClean){
            pageNum+=1;
        }
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        fragment.stopRefresh();
    }
}
