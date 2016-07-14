package com.floatingmuseum.mocloud.mainmovie.trending;

import com.floatingmuseum.mocloud.date.Repository;
import com.floatingmuseum.mocloud.date.entity.BaseMovie;
import com.floatingmuseum.mocloud.date.entity.Movie;
import com.floatingmuseum.mocloud.date.net.MoCloudFactory;
import com.floatingmuseum.mocloud.date.net.MoCloudService;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/7/13.
 */
public class TrendingRepo {
        protected MoCloudService service;
        public TrendingRepo(){
                service = MoCloudFactory.getInstance();
            }

                public void getMovieTrendingData(int pageNum, final Repository.DataCallback<List<BaseMovie>> callback){
        //        Logger.d("getMovieTrendingData()");
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
                //                        Logger.d("onNext:"+movieTrendings.get(0).getMovie().getTitle());
                                                getImagesByBaseMoive(movieTrendings,callback);
                                    }
                            });
            }

                protected void getImagesByBaseMoive(final List<BaseMovie> movies, final Repository.DataCallback callback){
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
                //                        Logger.d("onCompleted...callback:"+callback);
                                                callback.onSuccess(movies);
                                    }

                                        @Override
                                public void onError(Throwable e) {
                                        callback.onError(e);
                                    }

                                        @Override
                                public void onNext(Movie movie) {
                //                        Logger.d("getImagesByBaseMoive...onNext:"+movie.getTitle());
                                                mergeImageAndBaseMovie(movies,movie);
                                    }
                            });
        //        addToCompositeSubscription(s);
                    }

                public Observable<Movie> getMovieImage(String movieId){
                return service.getMovieImage(movieId)
                                .subscribeOn(Schedulers.io());
            }

                protected void mergeImageAndBaseMovie(List<BaseMovie> movies, Movie movie) {
                for(BaseMovie t : movies){
                        if(t.getMovie().getTitle().equals(movie.getTitle())){
                                t.getMovie().setImages(movie.getImages());
                            }
                   }
            }
}
