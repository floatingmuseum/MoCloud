package com.floatingmuseum.mocloud.data.repo;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.SimpleObserver;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.floatingmuseum.mocloud.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Floatingmuseum on 2017/9/12.
 */

public class MovieRepository extends Repository {

    private static MovieRepository repository;
    private MoCloudService service;

    private MovieRepository() {
        service = MoCloudFactory.getInstance();
    }

    public static MovieRepository getInstance() {
        if (repository == null) {
            synchronized (MovieRepository.class) {
                if (repository == null) {
                    repository = new MovieRepository();
                }
            }
        }
        return repository;
    }

    /**
     * Trending
     */
    public void getMovieTrendingData(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        Logger.d("Service对象Repository:" + service);
        final long startTime = System.currentTimeMillis();
        Logger.d("getMovieTrendingData...pageNum:" + pageNum);
        final List<BaseMovie> movies = new ArrayList<>();

        service.getMovieTrending(pageNum, limit)
                .compose(getEachPoster(movies, ImageCacheManager.TYPE_POSTER))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new SimpleObserver<List<BaseMovie>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieTrendingData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        Logger.d("getMovieTrendingData...onNext:" + movies);
                        long endTime = System.currentTimeMillis();
                        Logger.d("获取Trending数据时间:" + (endTime - startTime));
                        callback.onBaseDataSuccess(movies);
                        downloadMovieImage(movies, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * Most popular
     */
    public void getMoviePopularData(int pageNum, int limit, final DataCallback<List<Movie>> callback) {
        final List<Movie> movies = new ArrayList<>();
        service.getMoviePopular(pageNum, limit)
                .flatMap(new Func1<List<Movie>, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(List<Movie> movieData) {
                        movies.addAll(movieData);
                        return Observable.from(movies);
                    }
                })
                .flatMap(new Func1<Movie, Observable<ArtImage>>() {
                    @Override
                    public Observable<ArtImage> call(Movie movie) {
                        return getTmdbMovieImageObservable(movie);
                    }
                })
                .map(new Func1<ArtImage, Movie>() {
                    @Override
                    public Movie call(ArtImage image) {
                        return mergeMovieAndImage2(image, movies, ImageCacheManager.TYPE_POSTER);
                    }
                })
                .toList()
                .compose(RxUtil.<List<Movie>>threadSwitch())
                .subscribe(new SimpleObserver<List<Movie>>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        callback.onBaseDataSuccess(movies);
//                        downloadMovieImage(null, movies, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * Most play
     */
    public void getMoviePlayedData(String period, int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMoviePlayed(period, pageNum, limit)
                .compose(getEachPoster(movies, ImageCacheManager.TYPE_POSTER))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new SimpleObserver<List<BaseMovie>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMoviePlayedData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> baseMovies) {
                        Logger.d("getMoviePlayedData...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadMovieImage(movies, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * Most watch
     */
    public void getMovieWatchedData(String period, int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieWatched(period, pageNum, limit)
                .compose(getEachPoster(movies, ImageCacheManager.TYPE_POSTER))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new SimpleObserver<List<BaseMovie>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieWatchedData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> baseMovies) {
                        Logger.d("getMovieWatchedData...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadMovieImage(movies, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * Most collected
     */
    public void getMovieCollectedData(String period, int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieCollected(period, pageNum, limit)
                .compose(getEachPoster(movies, ImageCacheManager.TYPE_POSTER))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new SimpleObserver<List<BaseMovie>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieCollectedData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        Logger.d("getMovieCollectedData...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadMovieImage(movies, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * Anticipated
     */
    public void getMovieAnticipatedData(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieAnticipated(pageNum, limit)
                .compose(getEachPoster(movies, ImageCacheManager.TYPE_POSTER))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new SimpleObserver<List<BaseMovie>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieAnticipated...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        Logger.d("getMovieAnticipated...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadMovieImage(movies, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * BoxOffice
     */
    public void getMovieBoxOfficeData(final DataCallback<List<BaseMovie>> callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieBoxOffice()
                .compose(getEachPoster(movies, ImageCacheManager.TYPE_BACKDROP))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new SimpleObserver<List<BaseMovie>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieBoxOfficeData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        Logger.d("getMovieBoxOfficeData...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadMovieImage(movies, ImageCacheManager.TYPE_BACKDROP);
                    }
                });
    }
}
