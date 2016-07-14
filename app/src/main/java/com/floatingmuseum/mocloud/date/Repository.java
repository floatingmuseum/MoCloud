package com.floatingmuseum.mocloud.date;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.date.entity.BaseMovie;
import com.floatingmuseum.mocloud.date.entity.Movie;
import com.floatingmuseum.mocloud.date.net.MoCloudFactory;
import com.floatingmuseum.mocloud.date.net.MoCloudService;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/7/11.
 */
@Singleton
public class Repository{

    protected MoCloudService service;

    public interface DataCallback<T extends Object>{
        void onSuccess(T t);
        void onError(Throwable e);
    }

    @Inject
    public Repository(){
        service = MoCloudFactory.getInstance();
    }

    public void getMovieTrendingData(int pageNum, final DataCallback<List<BaseMovie>> callback){
        Logger.d("getMovieTrendingData");
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
                    public void onNext(List<BaseMovie> movies) {
                        callback.onSuccess(movies);
                    }
                });
    }

    public void getMoviePopularData(int pageNum,final DataCallback callback){
        service.getMoviePopular(pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
                        callback.onSuccess(movies);
                    }
                });
    }

    public void getMoviePlayedData(String period,int pageNum,final DataCallback callback){
        service.getMoviePlayed(period,pageNum)
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
                    public void onNext(List<BaseMovie> movies) {
                        callback.onSuccess(movies);
                    }
                });
    }

    public void getMovieWatchedData(String period,int pageNum,final DataCallback callback){
        service.getMovieWatched(period,pageNum)
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
                    public void onNext(List<BaseMovie> movies) {
                        callback.onSuccess(movies);
                    }
                });
    }

    public void getMovieCollectedData(String period, int pageNum, final DataCallback callback){
        service.getMovieCollected(period,pageNum)
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
                    public void onNext(List<BaseMovie> movies) {
                        callback.onSuccess(movies);
                    }
                });
    }

    public void getMovieAnticipatedData(int pageNum,final DataCallback callback){
        service.getMovieAnticipated(pageNum)
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
                    public void onNext(List<BaseMovie> movies) {
                        callback.onSuccess(movies);
                    }
                });
    }

    public void getMovieBoxOfficeData(final DataCallback callback){
        service.getMovieBoxOffice()
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
                    public void onNext(List<BaseMovie> movies) {
                        callback.onSuccess(movies);
                    }
                });
    }

    public Observable<Movie> getMovieImage(String movieId){
        return service.getMovieImage(movieId)
                .subscribeOn(Schedulers.io());
    }

    protected void getImagesByBaseMoive(final List<BaseMovie> movies, final DataCallback callback){
                Observable.from(movies).flatMap(new Func1<BaseMovie, Observable<Movie>>() {
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
    }

    public void getImagesByMovie(final List<Movie> movies, final DataCallback callback){
                Observable.from(movies).flatMap(new Func1<Movie, Observable<Movie>>() {
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
}
