package com.floatingmuseum.mocloud.base;

import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.net.MoCloudFactory;
import com.floatingmuseum.mocloud.model.net.MoCloudService;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class BaseRepo {
    protected MoCloudService service;
    protected DataCallback callback;
    protected CompositeSubscription mCompositeSubscription;
    public BaseRepo(){
        service = MoCloudFactory.getInstance();
    }

    public interface DataCallback<T extends Object>{
        void onSuccess(T t);
        void onError(Throwable e);
    }

    public Observable<Movie> getMovieImage(String movieId){
        return service.getMovieImage(movieId)
                .subscribeOn(Schedulers.io());
    }

    protected void getImagesByBaseMoive(final List<BaseMovie> movies){
        Subscription s = Observable.from(movies).flatMap(new Func1<BaseMovie, Observable<Movie>>() {
            @Override
            public Observable<Movie> call(BaseMovie trending) {
                return getMovieImage(trending.getMovie().getIds().getSlug());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                        callback.onSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        mergeImageAndBaseMovie(movies,movie);
                    }
                });
        addToCompositeSubscription(s);
    }

    public void getImagesByMovie(final List<Movie> movies){
        Subscription s = Observable.from(movies).flatMap(new Func1<Movie, Observable<Movie>>() {
            @Override
            public Observable<Movie> call(Movie movie) {
                return getMovieImage(movie.getIds().getSlug());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                        callback.onSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        mergeImageAndMovie(movies,movie);
                    }
                });
        addToCompositeSubscription(s);
    }

    /**
     * 将获取到的图片对象添加到对应的movie对象中
     */
    protected void mergeImageAndBaseMovie(List<BaseMovie> movies, Movie movie) {
        for(BaseMovie t : movies){
            if(t.getMovie().getTitle().equals(movie.getTitle())){
                t.getMovie().setImages(movie.getImages());
            }
        }
    }

    /**
     * 将获取到的图片对象添加到对应的movie对象中
     */
    protected void mergeImageAndMovie(List<Movie> movies,Movie movie) {
        for(Movie t : movies){
            if(t.getTitle().equals(movie.getTitle())){
                t.setImages(movie.getImages());
            }
        }
    }

    protected void addToCompositeSubscription(Subscription s){
        if(mCompositeSubscription==null){
            mCompositeSubscription = new CompositeSubscription();
        }

        mCompositeSubscription.add(s);
    }

    public void destroyCompositeSubscription(){
        Logger.d("destroy");
        if (mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
