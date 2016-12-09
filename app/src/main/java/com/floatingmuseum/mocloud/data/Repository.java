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
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
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
                        callback.onPeopleSuccess(people);
                    }
                });
    }

    private Observable<String> createIpObservable(final String url) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (url.equals("http://music.163.com/")){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    String ip = getIPByUrl(url);
                    subscriber.onNext(url + "..." + ip);
//                    Logger.d("RxJava测试...Emit Data -> ", url + " : " + ip);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    //subscriber.onError(e);
                    subscriber.onNext(null);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                    //subscriber.onError(e);
                    subscriber.onNext(null);
                }
                subscriber.onCompleted();
            }
        });
//        .subscribeOn(Schedulers.io())
    }

    private String getIPByUrl(String str) throws MalformedURLException, UnknownHostException {
        URL urls = new URL(str);
        String host = urls.getHost();
        String address = InetAddress.getByName(host).toString();
        int b = address.indexOf("/");
        return address.substring(b + 1);

    }

//    public void test() {
//        List<String> list;
//        Observable.from(urllist).flatMap(new Func1<String, Observable<String>>() {
//            @Override
//            public Observable<String> call(String url) {
//                Logger.d("Url:"+url);
//                return requestContent(url);
//            }
//        }).subscribeOn(Schedulers.io())
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String content) {
//                        Logger.d("Thread:"+Thread.currentThread().getName());
//                    }
//                });
//    }

    public Subscription getMovieTeam(String movieId, final MovieDetailCallback callback) {
        List<String> list = Arrays.asList("http://www.baidu.com/",
                "http://www.google.com/",
                "https://www.bing.com/",
                "http://www.github.com/",
                "https://www.v2ex.com/",
                "http://music.163.com/",
                "https://trakt.tv/",
                "http://www.jianshu.com/",
                "https://www.zhihu.com/",
                "https://www.themoviedb.org/");
        Observable.from(list).map(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                return createIpObservable(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Observable<String>>() {
                    @Override
                    public void call(Observable<String> stringObservable) {
//                        Logger.d("RxJava测试...Map:" + stringObservable);
                    }
                });

        Observable.from(list).concatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                Logger.d("RxJava测试...concatMap:" + s);
                return createIpObservable(s);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Logger.d("RxJava测试...concatMap:" + s);
            }
        });
        final long startTime = System.currentTimeMillis();
        Observable.from(list).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                Logger.d("RxJava测试...flatMap:" + s);
                return createIpObservable(s);
            }
        }).subscribeOn(Schedulers.io())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("RxJava测试...flatMap耗时" + (System.currentTimeMillis() - startTime));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        Logger.d("RxJava测试...flatMap:" + s + "...当前线程:" + Thread.currentThread().getName());
                    }
                });

        final List<Staff> finallyStaffs = new ArrayList<>();
        return service.getMovieTeam(movieId)
                .subscribeOn(Schedulers.io())
                .concatMap(new Func1<People, Observable<Staff>>() {
                    @Override
                    public Observable<Staff> call(People people) {
                        List<Staff> staffs = getPeople(people);
                        finallyStaffs.addAll(staffs);
                        return Observable.from(staffs);
                    }
                }).flatMap(new Func1<Staff, Observable<TmdbPeopleImage>>() {
                    @Override
                    public Observable<TmdbPeopleImage> call(Staff staff) {
                        Logger.d("串串Name:" + staff.getPerson().getName() + "...ID:" + staff.getPerson().getIds().getTmdb());
                        return service.getPeopleImage(staff.getPerson().getIds().getTmdb(), BuildConfig.TmdbApiKey);
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TmdbPeopleImage>() {
                    @Override
                    public void onCompleted() {
                        Logger.d("串串耗时" + (System.currentTimeMillis() - startTime));
                        if (finallyStaffs != null && finallyStaffs.size() != 0) {
                            for (Staff staff : finallyStaffs) {
                                Logger.d("串串Name:" + staff.getPerson().getName() + "...ImageUrl:" + staff.getTmdbPeopleImage().getProfiles().get(0).getFile_path());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("串串getMovieTeam...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TmdbPeopleImage tmdbPeopleImage) {
                        Logger.d("串串...获取图片数据中:" + tmdbPeopleImage.getId());
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

    public void getStaffDetail(int tmdbId, final DataCallback callback) {
        service.getStaff(tmdbId, BuildConfig.TmdbApiKey, "credits")
                .concatMap(new Func1<TmdbStaff, Observable<String>>() {
                    @Override
                    public Observable<String> call(TmdbStaff tmdbStaff) {
                        return Observable.from(tmdbStaff.getAlso_known_as());
//                        return service.getPeopleMovieCredits(tmdbStaff.getImdb_id());
                    }
                }).flatMap(new Func1<String, Observable<?>>() {
            @Override
            public Observable<?> call(String peopleCredit) {
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
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

    public void saveImage(String imageUrl) {
//        service.downloadImage(imageUrl)
//                .subscribeOn(Schedulers.io())
    }
}
