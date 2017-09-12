package com.floatingmuseum.mocloud.ui.movie.anticipated;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.repo.MovieRepository;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
class MovieAnticipatedPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {

    private MovieAnticipatedFragment fragment;


    MovieAnticipatedPresenter(@NonNull MovieAnticipatedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        MovieRepository.getInstance().getMovieAnticipatedData(getPageNum(shouldClean),limit,this);
//        repository.getMovieAnticipatedData(getPageNum(shouldClean),limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        fragment.refreshData(baseMovies,shouldClean);
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
        fragment.onError();
    }
}
