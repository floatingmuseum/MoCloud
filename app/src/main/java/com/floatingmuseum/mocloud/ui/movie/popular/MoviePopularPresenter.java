package com.floatingmuseum.mocloud.ui.movie.popular;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.repo.MovieRepository;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularPresenter extends ListPresenter implements DataCallback<List<Movie>> {

    private MoviePopularFragment fragment;


    public MoviePopularPresenter(@NonNull MoviePopularFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        MovieRepository.getInstance().getMoviePopularData(getPageNum(shouldClean),limit,this);
//        repository.getMoviePopularData(getPageNum(shouldClean),limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<Movie> movies) {
        fragment.refreshData(movies,shouldClean);
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
        fragment.stopRefresh();
    }
}
