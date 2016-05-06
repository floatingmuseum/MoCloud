package com.floatingmuseum.mocloud.mainmovie.watched;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.floatingmuseum.mocloud.model.entity.Movie;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedRepo extends BaseRepo {

    public MovieWatchedRepo(DataCallback<List<BaseMovie>> callback){
        this.callback = callback;
    }

    public void getData(String period,int pageNum){
        service.getMovieWatched(period,pageNum)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        getImagesByBaseMoive(movies);
                    }
                });
    }
}
