package com.floatingmuseum.mocloud.data;

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
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.data.entity.TmdbPeopleImage;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.floatingmuseum.mocloud.utils.ErrorUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


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

    public void getMovieTrendingData1(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        Logger.d("getMovieTrendingData1");
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieTrending(pageNum, limit)
                .flatMap(new Func1<List<BaseMovie>, Observable<BaseMovie>>() {
                    @Override
                    public Observable<BaseMovie> call(List<BaseMovie> baseMovies) {
                        movies.addAll(baseMovies);
                        return Observable.from(movies);
                    }
                }).flatMap(new Func1<BaseMovie, Observable<MovieImage>>() {
            @Override
            public Observable<MovieImage> call(BaseMovie baseMovie) {
                int tmdbID = baseMovie.getMovie().getIds().getTmdb();
                String imdbID = baseMovie.getMovie().getIds().getImdb();
                Logger.d("getMovieTrendingData1...ImdbID:" + imdbID + "...TmdbID:" + tmdbID + "...title:" + baseMovie.getMovie().getTitle());
//                File file = ImageCacheManager.hasCacheImage(tmdbID);
//                if (file != null) {
//                    return ImageCacheManager.localImage(tmdbID, file);
//                }
                return service.getMovieImages(imdbID, BuildConfig.FanrtApiKey).subscribeOn(Schedulers.io());
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieImage>() {
                    @Override
                    public void onCompleted() {
//                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieTrendingData1...onError");
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            Logger.d("getMovieTrendingData1...onError...Code:" + httpException.code());
                        }
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(MovieImage movieImage) {
                        Logger.d("getMovieTrendingData1...onNext:" + movieImage);
//                        Logger.d("Error测试...onNext:" + movieImage);
                        if (movieImage != null) {
//                            mergeMovieAndImage1(movieImage, movies);
//                            downLoadImage(movieImage);
                        }
                    }
                });
    }

    public void getMovieTrendingData(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        Logger.d("getMovieTrendingData");
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieTrending(pageNum, limit)
                .flatMap(new Func1<List<BaseMovie>, Observable<BaseMovie>>() {
                    @Override
                    public Observable<BaseMovie> call(List<BaseMovie> baseMovies) {
                        movies.addAll(baseMovies);
                        return Observable.from(movies);
                    }
                }).flatMap(new Func1<BaseMovie, Observable<TmdbMovieImage>>() {
            @Override
            public Observable<TmdbMovieImage> call(BaseMovie baseMovie) {
                int tmdbID = baseMovie.getMovie().getIds().getTmdb();
                Logger.d("getMovieTrendingData...TmdbID:" + tmdbID);
                File file = ImageCacheManager.hasCacheImage(tmdbID);
                if (file != null) {
                    return ImageCacheManager.localImage(tmdbID, file);
                }
                return service.getTmdbImages(baseMovie.getMovie().getIds().getTmdb(), BuildConfig.TmdbApiKey).subscribeOn(Schedulers.io());
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
                        Logger.d("getMovieTrendingData...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        Logger.d("getMovieTrendingData...onNext:" + movieImage);
//                        Logger.d("Error测试...onNext:" + movieImage);
                        if (movieImage != null) {
                            mergeMovieAndImage1(movieImage, movies);
                            downLoadImage(movieImage);
                        }
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

    public Subscription getMovieDetail(String movieId, final MovieDetailCallback callback) {

        return service.getMovieDetail(movieId)
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

    public Subscription getMovieTeam(int movieId, final MovieDetailCallback callback) {
        return service.getMovieTeam(movieId, BuildConfig.TmdbApiKey)
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
//                        callback.onPeopleSuccess(people);
                    }
                });
    }

    public Subscription getMovieTeam(String movieId, final MovieDetailCallback callback) {
        final List<Staff> finallyStaffs = new ArrayList<>();
        return service.getMovieTeam(movieId)
                .flatMap(new Func1<People, Observable<Staff>>() {
                    @Override
                    public Observable<Staff> call(People people) {
                        List<Staff> staffs = getPeople(people);
                        finallyStaffs.addAll(staffs);
                        return Observable.from(staffs);
                    }
                }).flatMap(new Func1<Staff, Observable<TmdbPeopleImage>>() {
                    @Override
                    public Observable<TmdbPeopleImage> call(Staff staff) {
                        return service.getPeopleImage(staff.getPerson().getIds().getTmdb(), BuildConfig.TmdbApiKey).subscribeOn(Schedulers.io());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TmdbPeopleImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onPeopleSuccess(finallyStaffs);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TmdbPeopleImage tmdbPeopleImage) {
                        for (Staff staff : finallyStaffs) {
                            if (staff.getPerson().getIds().getTmdb() == tmdbPeopleImage.getId()) {
                                staff.setTmdbPeopleImage(tmdbPeopleImage);
                            }
                        }
                    }
                });
    }

    private List<Staff> getPeople(People people) {
        List<Staff> staffs = new ArrayList<>();
        if (people.getCrew() != null && people.getCrew().getDirecting() != null) {
            List<Staff> directors = people.getCrew().getDirecting();
            if (directors.size() != 0) {
                for (Staff director : directors) {
                    if (director.getJob().equals("Director")) {
                        staffs.add(director);
                        break;
                    }
                }
            }
        }

        List<Staff> actors = people.getCast();
        if (actors != null && actors.size() != 0) {
            int actorRequestNumber = staffs.size() == 0 ? 4 : 3;
            actorRequestNumber = actors.size() < 3 ? actors.size() : actorRequestNumber;
            for (int i = 0; i < actorRequestNumber; i++) {
                staffs.add(actors.get(i));
            }
        }
        return staffs;
    }

    public void getPeopleImage(People people, final MovieDetailCallback callback) {
        final List<Staff> staffs = new ArrayList<>();
        List<Staff> directors = people.getCrew().getDirecting();
        if (directors != null && directors.size() != 0) {
            staffs.add(directors.get(0));
        }
        List<Staff> actors = people.getCast();
        if (actors != null && actors.size() != 0) {
            int actorRequestNumber = staffs.size() == 0 ? 4 : 3;
            actorRequestNumber = actors.size() < 3 ? actors.size() : actorRequestNumber;
            for (int i = 0; i < actorRequestNumber; i++) {
                staffs.add(actors.get(i));
            }
        }

        if (staffs.size() != 0) {
            Observable.from(staffs).flatMap(new Func1<Staff, Observable<TmdbPeopleImage>>() {
                @Override
                public Observable<TmdbPeopleImage> call(Staff staff) {
                    return service.getPeopleImage(staff.getPerson().getIds().getTmdb(), BuildConfig.TmdbApiKey);
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TmdbPeopleImage>() {
                        @Override
                        public void onCompleted() {
//                            callback.onPeopleSuccess(staffs);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(TmdbPeopleImage tmdbPeopleImage) {
                            Logger.d("onNext...getPeople...i'm working,even you destroyed activity");
                            for (Staff staff : staffs) {
                                if (staff.getPerson().getIds().getTmdb() == tmdbPeopleImage.getId()) {
                                    staff.setTmdbPeopleImage(tmdbPeopleImage);
                                }
                            }
                        }
                    });
        }
    }

    public Subscription getMovieComments(String movieId, String commentsSort, int limit, int page, final MovieDetailCallback movieDetailCallback, final MovieCommentsCallback commentsCallback) {
        return service.getComments(movieId, commentsSort, limit, page)
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

    public void getStaffDetail(String traktID, final DataCallback callback) {
        service.getStaff(traktID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Person>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Person person) {
                        callback.onBaseDataSuccess(person);
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
                .subscribe(new Observer<TraktToken>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("请求失败");
                        dataCallback.onError(e);
                    }

                    @Override
                    public void onNext(TraktToken traktToken) {
                        Logger.d("请求成功");
                        // TODO: 2016/9/18 请求成功 请求成功的逻辑应该相同，未测试
                        dataCallback.onBaseDataSuccess(traktToken);
                    }
                });
    }

    public void getUserSettings(final DataCallback callback) {
//        new Func1<Throwable, Observable<? extends UserSettings>>() {
//            @Override
//            public Observable<? extends UserSettings> call(Throwable throwable) {
//                Logger.d("UserSettings:出现异常");
//                if (ErrorUtil.is401Error(throwable)) {
//                    Logger.d("UserSettings:401异常");
//                    return getNewAccessToken().flatMap(new Func1<TraktToken, Observable<UserSettings>>() {
//                        @Override
//                        public Observable<UserSettings> call(TraktToken traktToken) {
//                            Logger.d("UserSettings:获取新Token");
//                            SPUtil.saveToken(traktToken);
//                            return service.getUserSettings();
//                        }
//                    });
//                }
//                return Observable.error(throwable);
//            }
//        }
        service.getUserSettings()
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserSettings()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserSettings>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("UserSettings:onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(UserSettings userSettings) {
                        Logger.d("UserSettings:onNext:"+userSettings);
                        callback.onBaseDataSuccess(userSettings);
                    }
                });
    }

    private Observable<? extends TraktToken> getNewAccessToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setRefresh_token(SPUtil.getRefreshToken());
        tokenRequest.setClient_id(BuildConfig.TraktID);
        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
        tokenRequest.setGrant_type(Constants.GRANT_TYPE_REFRESH_TOKEN);
        return service.getNewAccessToken(tokenRequest);
    }

    private <T>Func1<Throwable,? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> tobeResumed){
        return new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                Logger.d("UserSettings:出现异常");
                if (ErrorUtil.is401Error(throwable)) {
                    Logger.d("UserSettings:401异常");
                    return getNewAccessToken().flatMap(new Func1<TraktToken, Observable<T>>() {
                        @Override
                        public Observable<T> call(TraktToken traktToken) {
                            Logger.d("UserSettings:获取新Token");
                            SPUtil.saveToken(traktToken);
                            return tobeResumed;
                        }
                    });
                }
                return Observable.error(throwable);
            }
        };
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
                    return ImageCacheManager.localImage(tmdbID, file);
                }
                return service.getTmdbImages(baseMovie.getMovie().getIds().getTmdb(), BuildConfig.TmdbApiKey);
            }
        }).onErrorReturn(new Func1<Throwable, TmdbMovieImage>() {
            @Override
            public TmdbMovieImage call(Throwable throwable) {

                return null;
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
                        Logger.d("Error测试...getTmdbImages...onError");

                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        Logger.d("Error测试...onNext:" + movieImage);
                        if (movieImage != null) {
                            mergeMovieAndImage1(movieImage, movies);
                            downLoadImage(movieImage);
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
                    return ImageCacheManager.localImage(tmdbID, file);
                }
                Logger.d("Tmdb:" + movie.getIds().getTmdb() + "..." + BuildConfig.TmdbApiKey);
                return service.getTmdbImages(movie.getIds().getTmdb(), BuildConfig.TmdbApiKey);
            }
        }).onErrorReturn(new Func1<Throwable, TmdbMovieImage>() {
            @Override
            public TmdbMovieImage call(Throwable throwable) {
                return null;
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
                        Logger.d("Error测试...onNext:" + movieImage);
                        if (movieImage != null) {
                            mergeMovieAndImage2(movieImage, movies);
                            downLoadImage(movieImage);
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

    private void downLoadImage(TmdbMovieImage movieImage) {
        List<TmdbMovieImage.Posters> posters = movieImage.getPosters();
        if (movieImage.isHasCache() || posters == null || posters.size() < 1) {
            Logger.d("电影:" + movieImage.getId() + "...没有海报");
            return;
        }
        movieImage.setHasPoster(true);
        final TmdbMovieImage.Posters poster = posters.get(0);
        final String fileName = "TMDB..." + movieImage.getId() + StringUtil.getFileSuffix(poster.getFile_path());
        String posterUrl = StringUtil.buildPosterUrl(poster.getFile_path());
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
}
