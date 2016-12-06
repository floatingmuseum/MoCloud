package com.floatingmuseum.mocloud.data;


import android.net.Uri;

import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.Constants;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.MovieCommentsCallback;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieImage;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Floatingmuseum on 2016/7/11.
 */

public class Repository {

    private static Repository repository;
    protected MoCloudService service;

    public static final String COMMENTS_SORT_LIKES = "likes";
    public static final String COMMENTS_SORT_NEWEST = "newest";
    public static final String COMMENTS_SORT_OLDEST = "oldest";
    public static final String COMMENTS_SORT_REPLIES = "replies";

    public Repository() {
        service = MoCloudFactory.getInstance();
    }

    public static void init() {
        repository = new Repository();
    }

    public static Repository getInstance() {
        return repository;
    }

    public void getMovieTrendingData(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        Logger.d("getMovieTrendingData");
        service.getMovieTrending(pageNum, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieTrendingData...onError");
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Logger.d("trending Data on Error:" + exception.response().errorBody().toString());
                        }
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
//                        callback.onBaseDataSuccess(movies);
//                        getFanrtImagesByBaseMovies(movies,callback);
                        getTmdbImagesByBaseMovie(movies, callback);
                    }
                });
    }

    public void getMoviePopularData(int pageNum, int limit, final DataCallback callback) {
        service.getMoviePopular(pageNum, limit)
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
//                        callback.onBaseDataSuccess(movies);
//                        getFanrtImagesByMovies(movies,callback);
                        getTmdbImagesByMovie(movies, callback);
                    }
                });
    }

    public void getMoviePlayedData(String period, int pageNum, int limit, final DataCallback callback) {
        service.getMoviePlayed(period, pageNum, limit)
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
//                        callback.onBaseDataSuccess(movies);
//                        getFanrtImagesByBaseMovies(movies,callback);
                        getTmdbImagesByBaseMovie(movies, callback);
                    }
                });
    }

    public void getMovieWatchedData(String period, int pageNum, int limit, final DataCallback callback) {
        service.getMovieWatched(period, pageNum, limit)
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
//                        callback.onBaseDataSuccess(movies);
//                        getFanrtImagesByBaseMovies(movies,callback);
                        getTmdbImagesByBaseMovie(movies, callback);
                    }
                });
    }

    public void getMovieCollectedData(String period, int pageNum, int limit, final DataCallback callback) {
        service.getMovieCollected(period, pageNum, limit)
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
//                        callback.onBaseDataSuccess(movies);
//                        getFanrtImagesByBaseMovies(movies,callback);
                        getTmdbImagesByBaseMovie(movies, callback);
                    }
                });
    }

    public void getMovieAnticipatedData(int pageNum, int limit, final DataCallback callback) {
        service.getMovieAnticipated(pageNum, limit)
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
//                        callback.onBaseDataSuccess(movies);
//                        getFanrtImagesByBaseMovies(movies,callback);
                        getTmdbImagesByBaseMovie(movies, callback);
                    }
                });
    }

    public void getMovieBoxOfficeData(final DataCallback callback) {
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
//                        callback.onBaseDataSuccess(movies);
//                        getFanrtImagesByBaseMovies(movies,callback);
                        getTmdbImagesByBaseMovie(movies, callback);
                    }
                });
    }

    public void getMovieDetail(String movieId, final MovieDetailCallback callback) {
        service.getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Movie>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Movie movie) {
                        callback.onBaseDataSuccess(movie);
                    }
                });
    }

    public void getMoviePeople(int movieId, final MovieDetailCallback callback) {
        service.getMoviePeople(movieId, BuildConfig.TmdbApiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TmdbPeople>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbPeople people) {
                        callback.onPeopleSuccess(people);
                    }
                });
    }

    public void getMovieComments(String movieId, String commentsSort, int limit, int page, final MovieDetailCallback movieDetailCallback, final MovieCommentsCallback commentsCallback) {
        service.getComments(movieId, commentsSort, limit, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (movieDetailCallback != null) {
                            movieDetailCallback.onError(e);
                        } else {
                            commentsCallback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        if (movieDetailCallback != null) {
                            movieDetailCallback.onCommentsSuccess(comments);
                        } else {
                            commentsCallback.onBaseDataSuccess(comments);
                        }
                    }
                });
    }

    public void getStaffDetail(int tmdbID, final DataCallback callback){
        service.getStaff(tmdbID,BuildConfig.TmdbApiKey,"credits")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TmdbStaff>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbStaff tmdbStaff) {
                        callback.onBaseDataSuccess(tmdbStaff);
                    }
                });
    }

    public void getAccessToken(String code, final DataCallback dataCallback) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setCode(code);
        tokenRequest.setClient_id(BuildConfig.TraktID);
        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
        tokenRequest.setGrant_type(Constants.GRANT_TYPE_AUTHORIZATION_CODE);

        service.getToken(tokenRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<TraktToken>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dataCallback.onError(e);
                    }

                    @Override
                    public void onNext(Response<TraktToken> traktTokenResponse) {
                        // TODO: 2016/9/18 请求成功 请求成功的逻辑应该相同，未测试
                        dataCallback.onBaseDataSuccess(traktTokenResponse.body());
                    }
                });
//        getToken(tokenRequest, dataCallback,REQUEST_ACCESS_TOKEN);
    }

//    public Observable refreshToken() {
//        TokenRequest tokenRequest = new TokenRequest();
//        tokenRequest.setRefresh_token(SPUtil.getRefreshToken());
//        tokenRequest.setClient_id(BuildConfig.TraktID);
//        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
//        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
//        tokenRequest.setGrant_type(Constants.GRANT_TYPE_REFRESH_TOKEN);
//
//        service.getToken(tokenRequest)
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<Response<TraktToken>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(Response<TraktToken> traktTokenResponse) {
//                        TraktToken traktToken = traktTokenResponse.body();
//                        //if refresh token has expired
//
//                        if (traktTokenResponse.code() == 401) {
//                            traktToken.setRefresh_token_expired(true);
//                            Logger.d("error:" + traktTokenResponse.body().getError() + "...description:" + traktTokenResponse.body().getError_description());
//                        } else {
//                            SPUtil.saveToken(traktToken);
//                        }
//                    }
//                });
//    }

    private static final int REQUEST_ACCESS_TOKEN = 1;
    private static final int REQUEST_REFRESH_TOKEN = 2;

//    private void getToken(TokenRequest tokenRequest, final DataCallback dataCallback, final int type) {
//        service.getToken(tokenRequest)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Response<TraktToken>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        dataCallback.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(Response<TraktToken> traktTokenResponse) {
//                        // TODO: 2016/9/18 401表示请求的方法是刷新token，如果401需要重新请求token
//                        TraktToken traktToken = traktTokenResponse.body();
//                        if (traktTokenResponse.code() == 401) {
//                            //if refresh token has expired
//                            if (type == REQUEST_REFRESH_TOKEN) {
//                                traktToken.setRefresh_token_expired(true);
//                                dataCallback.onBaseDataSuccess(traktToken);
//                            }
//                            Logger.d("error:" + traktTokenResponse.body().getError() + "...description:" + traktTokenResponse.body().getError_description());
//                        } else {
//                            // TODO: 2016/9/18 请求成功 请求成功的逻辑应该相同，未测试
//                            dataCallback.onBaseDataSuccess(traktToken);
//                        }
//                    }
//                });
//    }

    public void getUserSettings(final DataCallback dataCallback) {

        service.getUserSettings(SPUtil.getAccessToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<UserSettings>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            Logger.d("UserSettings onError:" + ((HttpException) e).code());
                        }
                        dataCallback.onError(e);
                    }

                    @Override
                    public void onNext(Response<UserSettings> userSettingsResponse) {
                        if (userSettingsResponse.code() == 401) {
                            // TODO: 2016/9/18 状态码401表示令牌过期，刷新后再请求


                        } else {
                            dataCallback.onBaseDataSuccess(userSettingsResponse.body());
                        }
                    }
                });
    }

//    private <T> Func1<Throwable,? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> toBeResumed) {
//        return new Func1<Throwable, Observable<? extends T>>() {
//            @Override
//            public Observable<? extends T> call(Throwable throwable) {
//                if(is401Error(throwable)){
//                    return refreshToken().flatMap(new Func1<TraktToken, Observable<? extends T>>() {
//                        @Override
//                        public Observable<? extends T> call(TraktToken traktToken) {
//                            if(traktToken.getAccess_token() == null){
//                                //刷新token后还是401错误，表示refreshToken也过期了，提示用户重新登录
//                                return Observable.error();
//                            }
//                            SPUtil.saveToken(traktToken);
//                            return toBeResumed;
//                        }
//                    });
//                }
//                //非401错误
//                return Observable.error();
//            }
//        }
//    }

    private boolean is401Error(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            if (httpException.code() == Constants.STATUS_CODE_UNAUTHORISED) {
                return true;
            }
        }
        return false;
    }

    /**************************************************************************************************************************************************/

    public void getTmdbImagesByBaseMovie(final List<BaseMovie> movies, final DataCallback callback) {
        Observable.from(movies).flatMap(new Func1<BaseMovie, Observable<TmdbMovieImage>>() {
            @Override
            public Observable<TmdbMovieImage> call(BaseMovie baseMovie) {
                int tmdbID = baseMovie.getMovie().getIds().getTmdb();
                Logger.d("Tmdb:" + tmdbID);
                File file = ImageCacheManager.hasCacheImage(tmdbID);
                if (file != null) {
                    Uri uri = Uri.parse(file.toURI().toString());
                    return ImageCacheManager.localImage(tmdbID,uri);
                }
                return service.getTmdbImages(baseMovie.getMovie().getIds().getTmdb(), BuildConfig.TmdbApiKey);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getTmdbImages...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
//                        Logger.d("Movie:" + movieImage.getId() + "...PosterUrl:" + movieImage.getPosters().get(0).getFile_path());
                        mergeMovieAndImage1(movieImage, movies);
                        if (!movieImage.isHasCache()) {
                            String fileName = "TMDB..." + movieImage.getId() + StringUtil.getFileSuffix(movieImage.getPosters().get(0).getFile_path());
                            downLoadImage(movieImage.getPosters().get(0).getFile_path(), fileName);
                        }
                    }
                });
    }

    public void getTmdbImagesByMovie(final List<Movie> movies, final DataCallback callback) {
        Observable.from(movies).flatMap(new Func1<Movie, Observable<TmdbMovieImage>>() {
            @Override
            public Observable<TmdbMovieImage> call(Movie movie) {
                int tmdbID = movie.getIds().getTmdb();
                Logger.d("Tmdb:" + tmdbID);
                File file = ImageCacheManager.hasCacheImage(tmdbID);
                if (file != null) {
                    Uri uri = Uri.parse(file.toURI().toString());
                    return ImageCacheManager.localImage(tmdbID,uri);
                }
                Logger.d("Tmdb:" + movie.getIds().getTmdb() + "..." + BuildConfig.TmdbApiKey);
                return service.getTmdbImages(movie.getIds().getTmdb(), BuildConfig.TmdbApiKey);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getTmdbImages...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        mergeMovieAndImage2(movieImage, movies);
                        if (!movieImage.isHasCache()) {
                            String fileName = "TMDB..." + movieImage.getId() + StringUtil.getFileSuffix(movieImage.getPosters().get(0).getFile_path());
                            downLoadImage(movieImage.getPosters().get(0).getFile_path(), fileName);
                        }
                    }
                });
    }

    public void getFanrtImagesByMovies(final List<Movie> movies, final DataCallback callback) {
        Observable.from(movies).flatMap(new Func1<Movie, Observable<MovieImage>>() {
            @Override
            public Observable<MovieImage> call(Movie movie) {
                Logger.d("Imdb:" + movie.getIds().getImdb() + "..." + BuildConfig.FanrtApiKey);
                return service.getMovieImages(movie.getIds().getImdb(), BuildConfig.FanrtApiKey);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getFanrtImages...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MovieImage movieImage) {
                        Logger.d("Movie:" + movieImage.getName() + "...PosterUrl:" + movieImage.getMovieposter().get(0).getUrl());
                        mergeMovieAndImage(movieImage, movies);
                    }
                });
    }

    public void getFanrtImagesByBaseMovies(final List<BaseMovie> movies, final DataCallback callback) {
        Observable.from(movies).flatMap(new Func1<BaseMovie, Observable<MovieImage>>() {
            @Override
            public Observable<MovieImage> call(BaseMovie movie) {
                Logger.d("Imdb:" + movie.getMovie().getIds().getImdb() + "..." + BuildConfig.FanrtApiKey);
                return service.getMovieImages(movie.getMovie().getIds().getTmdb(), BuildConfig.FanrtApiKey);
            }
        }).subscribeOn(Schedulers.io())
//                .onExceptionResumeNext(Observable.<MovieImage>empty())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getFanrtImages...onError" + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MovieImage movieImage) {
//                        Logger.d("Movie:" + movieImage.getName() + "...PosterUrl:" + movieImage.getMovieposter().get(0).getUrl());
                        mergeBaseMovieAndImage(movieImage, movies);
                    }
                });
    }

    private void mergeMovieAndImage(MovieImage image, List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getIds().getImdb().equals(image.getImdb_id())) {
                movie.setImages(image);
            }
        }
    }

    private void mergeBaseMovieAndImage(MovieImage image, List<BaseMovie> movies) {
        for (BaseMovie movie : movies) {
            if (movie.getMovie().getIds().getImdb().equals(image.getImdb_id())) {
                movie.getMovie().setImages(image);
            }
        }
    }

    private void mergeMovieAndImage1(TmdbMovieImage image, List<BaseMovie> movies) {
        for (BaseMovie movie : movies) {
            if (movie.getMovie().getIds().getTmdb() == image.getId()) {
                movie.getMovie().setImage(image);
            }
        }
    }

    private void mergeMovieAndImage2(TmdbMovieImage image, List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getIds().getTmdb() == image.getId()) {
                movie.setImage(image);
            }
        }
    }

    private void downLoadImage(String url, final String fileName) {
        String posterUrl = StringUtil.buildPosterUrl(url);
        service.downloadImage(posterUrl)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("图片下载出错:" + fileName);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        ImageCacheManager.writeToDisk(responseBody, fileName);
                    }
                });
    }

    public void saveImage(String imageUrl) {
//        service.downloadImage(imageUrl)
//                .subscribeOn(Schedulers.io())
    }
}
