package com.floatingmuseum.mocloud.ui.trakt_mainmovie.anticipated;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieAnticipatedPresenter extends Presenter implements MovieAnticipatedContract.Presenter, DataCallback<List<BaseMovie>> {

    private MovieAnticipatedContract.View anticipatedView;
    private int pageNum = 1;
    private int limit = 12;
    protected boolean shouldClean;


    public MovieAnticipatedPresenter(@NonNull MovieAnticipatedContract.View anticipatedView){
        this.anticipatedView = anticipatedView;
    }

    @Override
    public void start(boolean shouldClean) {
        pageNum = shouldClean?1:++pageNum;
        this.shouldClean =shouldClean;
        repository.getMovieAnticipatedData(pageNum,limit,this);
    }

    @Override
    public void onBaseDataSuccess(List<BaseMovie> baseMovies) {
        for (BaseMovie baseMovie : baseMovies) {
            Logger.d("Error测试...Movie:"+baseMovie.getMovie().getTitle()+"...Image:"+baseMovie.getMovie().getImage());
        }
        anticipatedView.refreshData(baseMovies,shouldClean);
        anticipatedView.stopRefresh();
    }

    public int getLimit(){
        return limit;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        anticipatedView.stopRefresh();
    }

    @Override
    public void onDestroy() {

    }
}
