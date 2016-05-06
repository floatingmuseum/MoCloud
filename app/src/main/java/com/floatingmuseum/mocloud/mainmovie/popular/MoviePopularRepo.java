package com.floatingmuseum.mocloud.mainmovie.popular;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.Movie;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularRepo extends BaseRepo{
    public MoviePopularRepo(BaseRepo.DataCallback<List<Movie>> callback){
        this.callback = callback;
    }

    public void getData(int pageNum){
        service.getMoviePopular(pageNum)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        getImagesByMovie(movies);
                    }
                });
    }
}
