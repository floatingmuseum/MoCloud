package com.floatingmuseum.mocloud.data;

import android.text.TextUtils;
import android.text.format.DateFormat;

import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.Constants;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.MovieCommentsCallback;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.floatingmuseum.mocloud.ui.login.LoginActivity;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.R.attr.type;

/**
 * Created by Floatingmuseum on 2016/7/11.
 */
@Singleton
public class Repository {

    protected MoCloudService service;
    public static final String COMMENTS_SORT_LIKES = "likes";
    public static final String COMMENTS_SORT_NEWEST = "newest";
    public static final String COMMENTS_SORT_OLDEST = "oldest";
    public static final String COMMENTS_SORT_REPLIES = "replies";

    @Inject
    public Repository() {
        service = MoCloudFactory.getInstance();
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
                        if (e instanceof HttpException) {
                            HttpException exception = (HttpException) e;
                            Logger.d("trending Data on Error:" + exception.response().errorBody().toString());
                        }
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        callback.onBaseDataSuccess(movies);
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
                        callback.onBaseDataSuccess(movies);
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
                        callback.onBaseDataSuccess(movies);
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
                        callback.onBaseDataSuccess(movies);
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
                        callback.onBaseDataSuccess(movies);
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
                        callback.onBaseDataSuccess(movies);
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
                        callback.onBaseDataSuccess(movies);
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

    public void getMoviePeople(String movieId, final MovieDetailCallback callback) {
        service.getMoviePeople(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<People>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(People people) {
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

    public Observable refreshToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setRefresh_token(SPUtil.getRefreshToken());
        tokenRequest.setClient_id(BuildConfig.TraktID);
        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
        tokenRequest.setGrant_type(Constants.GRANT_TYPE_REFRESH_TOKEN);

        service.getToken(tokenRequest)
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<TraktToken>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Response<TraktToken> traktTokenResponse) {
                        TraktToken traktToken = traktTokenResponse.body();
                        //if refresh token has expired

                        if (traktTokenResponse.code() == 401) {
                            traktToken.setRefresh_token_expired(true);
                            Logger.d("error:" + traktTokenResponse.body().getError() + "...description:" + traktTokenResponse.body().getError_description());
                        } else {
                            SPUtil.saveToken(traktToken);
                        }
                    }
                });
    }

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

    public void getUserSettings(String accessToken, final DataCallback dataCallback) {
        service.getUserSettings(accessToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Response<UserSettings>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
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

    public void getUserSettings(String accessToken) {
        service.getUserSettings(accessToken)
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<?>>() {
                    @Override
                    public Observable<?> call(Observable<? extends Throwable> observable) {
                        return null;
                    }
                });
    }

    public void saveImage(String imageUrl) {
//        service.downloadImage(imageUrl)
//                .subscribeOn(Schedulers.io())
    }
}
