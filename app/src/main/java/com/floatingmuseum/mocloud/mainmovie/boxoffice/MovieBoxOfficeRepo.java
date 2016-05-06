package com.floatingmuseum.mocloud.mainmovie.boxoffice;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.List;

import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficeRepo extends BaseRepo {
    public MovieBoxOfficeRepo(DataCallback<List<BaseMovie>> callback){
        this.callback = callback;
    }

    public void getData(){
        service.getMovieBoxOffice()
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
