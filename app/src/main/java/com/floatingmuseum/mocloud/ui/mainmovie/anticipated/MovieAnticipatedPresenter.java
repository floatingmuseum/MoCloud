package com.floatingmuseum.mocloud.ui.mainmovie.anticipated;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieAnticipatedPresenter extends ListPresenter implements DataCallback<List<BaseMovie>> {

    private MovieAnticipatedFragment fragment;


    public MovieAnticipatedPresenter(@NonNull MovieAnticipatedFragment fragment){
        this.fragment = fragment;
    }

    @Override
    public void start(boolean shouldClean) {
        repository.getMovieAnticipatedData(getPageNum(shouldClean),limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
//        for (BaseMovie baseMovie : baseMovies) {
//            Logger.d("Error测试...Movie:"+baseMovie.getMovie().getTitle()+"...Image:"+baseMovie.getMovie().getImage());
//        }
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
