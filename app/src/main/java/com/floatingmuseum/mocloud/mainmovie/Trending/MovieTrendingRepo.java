package com.floatingmuseum.mocloud.mainmovie.trending;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmusuem on 2016/4/19.
 */
public class MovieTrendingRepo extends BaseRepo{

    public MovieTrendingRepo(DataCallback<List<BaseMovie>> callback){
        this.callback = callback;
    }

    public void getData(int pageNum){
        service.getMovieTrending(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movieTrendings) {
                        getImagesByBaseMoive(movieTrendings);
                    }
                });
    }

//    public void getImages(final List<BaseMovie> movieTrendings){
//        Observable.from(movieTrendings).flatMap(new Func1<BaseMovie, Observable<Movie>>() {
//            @Override
//            public Observable<Movie> call(BaseMovie trending) {
//                return getMovie(trending.getMovie().getIds().getSlug());
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Movie>() {
//                    @Override
//                    public void onCompleted() {
//                        callback.onSuccess(movieTrendings);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        callback.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(Movie movie) {
//                        mergeImageAndBaseMovie(movieTrendings,movie);
//                    }
//                });
//    }
}
