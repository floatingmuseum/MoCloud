package com.floatingmuseum.mocloud.data;

import android.Manifest;

import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.Constants;
import com.floatingmuseum.mocloud.data.callback.CommentReplyCallback;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.callback.UserDetailCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieImage;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.Stats;
import com.floatingmuseum.mocloud.data.entity.TmdbImage;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.data.entity.TmdbPeopleImage;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;
import com.floatingmuseum.mocloud.data.entity.TmdbStaffMovieCredits;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.floatingmuseum.mocloud.utils.ErrorUtil;
import com.floatingmuseum.mocloud.utils.PermissionsUtil;
import com.floatingmuseum.mocloud.utils.RxUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
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

    /******************************************
     * 首页数据
     ********************************************************/

    public Subscription getMoviePopular(int pageNum, final DataCallback callback) {
        Logger.d("测试开始...TmdbMovieDataList");
        return service.getMoviePopular(pageNum, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
                .subscribe(new Observer<TmdbMovieDataList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
                        Logger.d("测试new api...getMoviePopular...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
                        callback.onBaseDataSuccess(tmdbMovieDataList);
                    }
                });
    }

    public Subscription getMovieNowPlaying(int pagNum, final DataCallback callback) {
        return service.getMovieNowPlaying(pagNum, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
                .subscribe(new Observer<TmdbMovieDataList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
                        Logger.d("测试new api...getMovieNowPlaying...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
                        callback.onBaseDataSuccess(tmdbMovieDataList);
                    }
                });
    }

    public Subscription getMovieTopRated(int pagNum, final DataCallback callback) {
        return service.getMovieTopRated(pagNum, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
                .subscribe(new Observer<TmdbMovieDataList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
                        Logger.d("测试new api...getMovieTopRated...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
                        callback.onBaseDataSuccess(tmdbMovieDataList);
                    }
                });
    }

    public Subscription getMovieUpcoming(int pagNum, final DataCallback callback) {
        return service.getMovieUpcoming(pagNum, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
                .subscribe(new Observer<TmdbMovieDataList>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
                        Logger.d("测试new api...getMovieUpcoming...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
                        callback.onBaseDataSuccess(tmdbMovieDataList);
                    }
                });
    }

    /******************************************
     * 剧目详情
     ********************************************************/

    public Subscription getMovieDetail(String movieId, final MovieDetailCallback callback) {
        return service.getMovieDetail(movieId)
                .compose(RxUtil.<Movie>threadSwitch())
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

    public Subscription getMovieDetail(int tmdbId, final MovieDetailCallback callback) {
        return service.getMovieDetail(tmdbId, BuildConfig.TmdbApiKey,"credits")
                .compose(RxUtil.<TmdbMovieDetail>threadSwitch())
                .subscribe(new Observer<TmdbMovieDetail>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TmdbMovieDetail tmdbMovieDetail) {
                        if (tmdbMovieDetail != null) {
                            Logger.d("测试new api...TmdbMovieDetail:" + tmdbMovieDetail.getTitle());
                            callback.onBaseDataSuccess(tmdbMovieDetail);
                        }
                    }
                });
    }

    public Subscription getMovieTeam(int tmdbId, final MovieDetailCallback callback) {
        return service.getMovieTeam(tmdbId, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<TmdbPeople>threadSwitch())
                .subscribe(new Observer<TmdbPeople>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbPeople tmdbPeople) {
//                        callback.onPeopleSuccess(tmdbPeople);
                    }
                });
    }

    public Subscription getMovieTraktRatings(String imdbId, final MovieDetailCallback callback) {
        return service.getMovieTraktRatings(imdbId)
                .compose(RxUtil.<Ratings>threadSwitch())
                .subscribe(new Observer<Ratings>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Ratings ratings) {
                        callback.onRatingsSuccess(ratings);
                    }
                });
    }

    private String getImageUrl(TmdbPeopleImage tmdbPeopleImage) {
        List<TmdbPeopleImage.Profiles> profiles = tmdbPeopleImage.getProfiles();
        if (profiles != null && profiles.size() > 0) {
            TmdbPeopleImage.Profiles profile = profiles.get(0);
            if (profile != null && profile.getFile_path() != null && profile.getFile_path().length() > 0) {
                return profile.getFile_path();
            }
        }
        return null;
    }

    private String getImageUrl(TmdbMovieImage tmdbMovieImage) {
        List<TmdbMovieImage.Posters> posters = tmdbMovieImage.getPosters();
        if (posters != null && posters.size() != 0) {
            return posters.get(0).getFile_path();
        }
        return null;
    }

    /******************************************
     * 评论数据
     ********************************************************/

    public Subscription getMovieComments(String movieId, String commentsSort, int limit, int page, final MovieDetailCallback movieDetailCallback, final CommentsCallback commentsCallback) {
        return service.getComments(movieId, commentsSort, limit, page)
                .compose(RxUtil.<List<Comment>>threadSwitch())
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

    public Subscription getCommentReplies(long commentId, final DataCallback callback) {
        return service.getCommentReplies(commentId)
                .compose(RxUtil.<List<Comment>>threadSwitch())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getCommentReplies...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        Logger.d("getCommentReplies...onNext");
                        callback.onBaseDataSuccess(comments);
                    }
                });
    }

    public Subscription sendReply(long id, Reply reply, final CommentReplyCallback callback) {
        Logger.d("sendReply:" + id + "..." + reply.getComment());
        return service.sendReply(id, reply)
                .onErrorResumeNext(refreshTokenAndRetry(service.sendReply(id, reply)))
                .compose(RxUtil.<Comment>threadSwitch())
                .subscribe(new Observer<Comment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("sendReply...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Comment comment) {
                        callback.onSendReplySuccess(comment);
                    }
                });
    }

    public Subscription sendComment(final Comment comment, String imdb_id, final CommentsCallback callback) {
        // TODO: 2017/1/3 comment在设置了movie后，在refresh方法中的comment理论上应该也是带有movie对象的，待测试 
        return service.getMovieDetail(imdb_id).flatMap(new Func1<Movie, Observable<Comment>>() {
            @Override
            public Observable<Comment> call(Movie movie) {
                comment.setMovie(movie);
                return service.sendComment(comment);
            }
        }).onErrorResumeNext(refreshTokenAndRetry(service.sendComment(comment)))
                .compose(RxUtil.<Comment>threadSwitch())
                .subscribe(new Observer<Comment>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Comment comment) {
                        callback.onSendCommentSuccess(comment);
                    }
                });
    }

    /******************************************
     * 影人数据
     ********************************************************/

    public Subscription getStaffDetail(int tmdbId, final DataCallback callback) {
        return service.getStaff(tmdbId, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<TmdbStaff>threadSwitch())
                .subscribe(new Observer<TmdbStaff>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(TmdbStaff staff) {
                        callback.onBaseDataSuccess(staff);
                    }
                });
    }

    public Subscription getStaffMovieCredits(int tmdbId, final DataCallback callback) {
        return service.getStaffMovieCredits(tmdbId, BuildConfig.TmdbApiKey)
                .map(new Func1<TmdbStaffMovieCredits, List<Staff>>() {
                    @Override
                    public List<Staff> call(TmdbStaffMovieCredits credits) {
                        Logger.d("日期对比:map");
                        return DataMachine.mixingStaffWorks(credits);
                    }
                })
                .compose(RxUtil.<List<Staff>>threadSwitch())
                .subscribe(new Observer<List<Staff>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("日期对比:onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Staff> works) {
                        Logger.d("日期对比:onNext");
                        callback.onBaseDataSuccess(works);
                    }
                });
    }

    /******************************************
     * OAUTH
     ********************************************************/

    public void getAccessToken(String code, final DataCallback dataCallback) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setCode(code);
        tokenRequest.setClient_id(BuildConfig.TraktID);
        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
        tokenRequest.setGrant_type(Constants.GRANT_TYPE_AUTHORIZATION_CODE);

        service.getToken(tokenRequest)
                .compose(RxUtil.<TraktToken>threadSwitch())
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
                        dataCallback.onBaseDataSuccess(traktToken);
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

    private <T> Func1<Throwable, ? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> tobeResumed) {
        return new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                Logger.d("refreshTokenAndRetry:出现异常");
                if (ErrorUtil.is401Error(throwable)) {
                    Logger.d("refreshTokenAndRetry:401异常");
                    return getNewAccessToken().flatMap(new Func1<TraktToken, Observable<T>>() {
                        @Override
                        public Observable<T> call(TraktToken traktToken) {
                            Logger.d("refreshTokenAndRetry:获取新Token");
                            SPUtil.saveToken(traktToken);
                            return tobeResumed;
                        }
                    });
                }
                return Observable.error(throwable);
            }
        };
    }

    /******************************************
     * 用户数据
     ********************************************************/

    public Subscription getUserSettings(final DataCallback callback) {
        return service.getUserSettings()
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserSettings()))
                .compose(RxUtil.<UserSettings>threadSwitch())
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
                        Logger.d("UserSettings:onNext:" + userSettings);
                        callback.onBaseDataSuccess(userSettings);
                    }
                });
    }

    public Subscription getUserFollowers(String slug, final UserDetailCallback callback) {
        return service.getUserFollowers(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserFollowers(slug)))
                .compose(RxUtil.<List<Follower>>threadSwitch())
                .subscribe(new Observer<List<Follower>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("UserData:getUserFollowers...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Follower> followers) {
                        callback.onUserFollowersSuccess(followers);
                    }
                });
    }

    public Subscription getUserFollowing(String slug, final UserDetailCallback callback) {
        return service.getUserFollowing(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserFollowing(slug)))
                .compose(RxUtil.<List<Follower>>threadSwitch())
                .subscribe(new Observer<List<Follower>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("UserData:getUserFollowing...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Follower> followers) {
                        callback.onUserFollowingSuccess(followers);
                    }
                });
    }

    public Subscription getUserStats(String slug, final UserDetailCallback callback) {
        return service.getUserStats(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserStats(slug)))
                .compose(RxUtil.<Stats>threadSwitch())
                .subscribe(new Observer<Stats>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("UserData:getUserStats...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Stats stats) {
                        if (stats != null) {
                            Logger.d("UserData:getUserStats...onNext:" + stats.getMovies().getMinutes());
                        }
                        callback.onUserStatsSuccess(stats);
                    }
                });
    }

    /*******************************************************************
     * 图片数据
     *******************************************************************************/

    public void getMoviePoster(int tmdbId, final MovieDetailCallback callback) {
        getTmdbMovieImage(tmdbId).compose(RxUtil.<TmdbMovieImage>threadSwitch())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TmdbMovieImage tmdbMovieImage) {
//                        callback.onPosterSuccess(tmdbMovieImage);
                    }
                });

    }

    /*******************************************************************
     * 图片处理
     *******************************************************************************/

    public void getTmdbImagesByBaseMovie(final List<BaseMovie> movies, final DataCallback callback) {
        Observable.from(movies).flatMap(new Func1<BaseMovie, Observable<TmdbMovieImage>>() {
            @Override
            public Observable<TmdbMovieImage> call(BaseMovie baseMovie) {
                return getTmdbMovieImage(baseMovie.getMovie().getIds().getTmdb());
//                int tmdbID = baseMovie.getMovie().getIds().getTmdb();
//                Logger.d("Tmdb:" + tmdbID);
//                File file = ImageCacheManager.hasCacheImage(tmdbID, ImageCacheManager.TYPE_POSTER);
//                if (file != null) {
//                    return ImageCacheManager.localPosterImage(tmdbID, file);
//                }
//                return service.getTmdbImages(baseMovie.getMovie().getIds().getTmdb(), BuildConfig.TmdbApiKey);
            }
        }).onErrorReturn(new Func1<Throwable, TmdbMovieImage>() {
            @Override
            public TmdbMovieImage call(Throwable throwable) {
                return null;
            }
        }).compose(RxUtil.<TmdbMovieImage>threadSwitch())
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
                            String imageUrl = getImageUrl(movieImage);
                            downLoadImage(movieImage, imageUrl, ImageCacheManager.TYPE_POSTER);
                        }
                    }
                });
    }

    private Observable<TmdbMovieImage> getTmdbMovieImage(int tmdbID) {
        File file = ImageCacheManager.hasCacheImage(tmdbID, ImageCacheManager.TYPE_POSTER);
        if (file != null) {
            return ImageCacheManager.localPosterImage(tmdbID, file);
        }
        return service.getTmdbImages(tmdbID, BuildConfig.TmdbApiKey);
    }

    public void getTmdbImagesByMovie(final List<Movie> movies, final DataCallback callback) {
        Observable.from(movies).flatMap(new Func1<Movie, Observable<TmdbMovieImage>>() {
            @Override
            public Observable<TmdbMovieImage> call(Movie movie) {
                int tmdbID = movie.getIds().getTmdb();
                Logger.d("Tmdb:" + tmdbID);
                File file = ImageCacheManager.hasCacheImage(tmdbID, ImageCacheManager.TYPE_POSTER);
                if (file != null) {
                    return ImageCacheManager.localPosterImage(tmdbID, file);
                }
                Logger.d("Tmdb:" + movie.getIds().getTmdb() + "..." + BuildConfig.TmdbApiKey);
                return service.getTmdbImages(movie.getIds().getTmdb(), BuildConfig.TmdbApiKey);
            }
        }).onErrorReturn(new Func1<Throwable, TmdbMovieImage>() {
            @Override
            public TmdbMovieImage call(Throwable throwable) {
                return null;
            }
        }).compose(RxUtil.<TmdbMovieImage>threadSwitch())
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
                            String imageUrl = getImageUrl(movieImage);
                            downLoadImage(movieImage, imageUrl, ImageCacheManager.TYPE_POSTER);
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

    private void downLoadImage(TmdbImage image, String imageUrl, final int imageType) {
        if (imageUrl == null) {
            return;
        }
        if (imageType == ImageCacheManager.TYPE_POSTER) {
            image.setHasPoster(true);
        } else {
            image.setHasAvatar(true);
        }

        if (!PermissionsUtil.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.d("没有读写权限，不下载");
            return;
        }

        final String fileName = "TMDB-" + image.getId() + StringUtil.getFileSuffix(imageUrl);
        String url = StringUtil.buildPosterUrl(imageUrl);
        service.downloadImage(url)
                .subscribeOn(Schedulers.io())
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
                        ImageCacheManager.writeToDisk(responseBody, fileName, imageType);
                    }
                });
    }
}
