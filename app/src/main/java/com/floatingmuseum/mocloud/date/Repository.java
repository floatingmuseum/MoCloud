package com.floatingmuseum.mocloud.date;

import com.floatingmuseum.mocloud.base.BaseRepo;
import com.floatingmuseum.mocloud.mainmovie.trending.MovieTrendingFragment;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.net.MoCloudFactory;
import com.floatingmuseum.mocloud.model.net.MoCloudService;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Floatingmuseum on 2016/7/11.
 */
@Singleton
public class Repository extends BaseRepo{

    @Inject
    public Repository(){
        service = MoCloudFactory.getInstance();
    }

    public void getMovieTrendingData(int pageNum, final DataCallback<List<BaseMovie>> callback){
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
                        Logger.d("onNext:"+movieTrendings.get(0).getMovie().getTitle());
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
                        Logger.d("Popular...onNext:"+movies.get(0).getTitle()+"..."+callback);
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


    protected MoCloudService service;
    protected CompositeSubscription mCompositeSubscription;
//    public Repository(){
//        service = MoCloudFactory.getInstance();
//    }

    public interface DataCallback<T extends Object>{
        void onSuccess(T t);
        void onError(Throwable e);
    }

    public Observable<Movie> getMovieImage(String movieId){
        return service.getMovieImage(movieId)
                .subscribeOn(Schedulers.io());
    }

    protected void getImagesByBaseMoive(final List<BaseMovie> movies, final DataCallback callback){
//        Subscription s =
                Observable.from(movies).flatMap(new Func1<BaseMovie, Observable<Movie>>() {
            @Override
            public Observable<Movie> call(BaseMovie trending) {
                Logger.d("getMovieImage");
                return getMovieImage(trending.getMovie().getIds().getSlug());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Movie>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("onCompleted...callback:"+callback);
                        callback.onSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        Logger.d("getImagesByBaseMoive...onNext:"+movie.getTitle());
                        mergeImageAndBaseMovie(movies,movie);
                    }
                });
//        addToCompositeSubscription(s);
    }

    public void getImagesByMovie(final List<Movie> movies, final DataCallback callback){
//        Subscription s =
                Observable.from(movies).flatMap(new Func1<Movie, Observable<Movie>>() {
            @Override
            public Observable<Movie> call(Movie movie) {
                Logger.d("getMovieImage:"+movie.getTitle());
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
                        Logger.d("Popular..getImagesByMovie..onNext:"+movie.getTitle()+"..."+callback);
                        mergeImageAndMovie(movies,movie);
                    }
                });
//        addToCompositeSubscription(s);
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
        if (mCompositeSubscription!=null){
            mCompositeSubscription.unsubscribe();
        }
    }
}
