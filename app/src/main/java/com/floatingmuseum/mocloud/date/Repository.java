package com.floatingmuseum.mocloud.date;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.mainmovie.trending.MovieTrendingFragment;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/7/11.
 */
@Singleton
public class Repository extends BaseRepo{

    @Inject
    public Repository(){

    }

    public void getMovieTrendingData(int pageNum, final DataCallback callback){
        Logger.d("getMovieTrendingData()");
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
                        getImagesByBaseMoive(movieTrendings,callback);
                    }
                });
    }

    public void getMoviePopularData(int pageNum,final DataCallback callback){
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
                        getImagesByMovie(movies,callback);
                    }
                });
    }

    public void getMoviePlayedData(String period,int pageNum,final DataCallback callback){
        service.getMoviePlayed(period,pageNum)
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
                        getImagesByBaseMoive(movies,callback);
                    }
                });
    }

    public void getMovieWatchedData(String period,int pageNum,final DataCallback callback){
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
                        getImagesByBaseMoive(movies,callback);
                    }
                });
    }

    public void getMovieCollectedData(String period, int pageNum, final DataCallback callback){
        service.getMovieCollocted(period,pageNum)
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
                        getImagesByBaseMoive(movies,callback);
                    }
                });
    }

    public void getMovieAnticipatedData(String period,int pageNum,final DataCallback callback){
        service.getMovieCollocted(period,pageNum)
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
                        getImagesByBaseMoive(movies,callback);
                    }
                });
    }

    public void getMovieBoxOfficeData(final DataCallback callback){
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
                        getImagesByBaseMoive(movies,callback);
                    }
                });
    }
}
