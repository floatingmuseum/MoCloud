package com.floatingmuseum.mocloud.data;

import android.Manifest;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.Constants;
import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.data.callback.CommentReplyCallback;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.callback.RecommendationsCallback;
import com.floatingmuseum.mocloud.data.callback.UserDetailCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.LastActivities;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieImage;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.StaffImages;
import com.floatingmuseum.mocloud.data.entity.Stats;
import com.floatingmuseum.mocloud.data.entity.TmdbImage;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPersonImage;
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
import java.util.List;
import java.util.concurrent.ExecutionException;


import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by Floatingmuseum on 2016/7/11.
 */

public class Repository {

    private static Repository repository;
    protected MoCloudService service;
    private MovieTeam team;

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

    /**
     * 趋势
     */
    public void getMovieTrendingData(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        Logger.d("getMovieTrendingData");
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieTrending(pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieTrendingData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        Logger.d("getMovieTrendingData...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadImage(movies, null, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * 流行
     */
    public void getMoviePopularData(int pageNum, int limit, final DataCallback callback) {
        final List<Movie> movies = new ArrayList<>();
        service.getMoviePopular(pageNum, limit)
                .flatMap(new Func1<List<Movie>, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> call(List<Movie> movieDatas) {
                        movies.addAll(movieDatas);
                        return Observable.from(movies);
                    }
                }).flatMap(new Func1<Movie, Observable<TmdbMovieImage>>() {
            @Override
            public Observable<TmdbMovieImage> call(Movie movie) {
                return getTmdbMovieImageObservable(movie);
            }
        }).map(new Func1<TmdbMovieImage, Movie>() {
            @Override
            public Movie call(TmdbMovieImage tmdbMovieImage) {
                return mergeMovieAndImage2(tmdbMovieImage, movies);
            }
        }).toList()
                .compose(RxUtil.<List<Movie>>threadSwitch())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        callback.onBaseDataSuccess(movies);
                        downloadImage(null, movies, ImageCacheManager.TYPE_POSTER);
                    }
                });

    }

    /**
     * 最多play
     */
    public void getMoviePlayedData(String period, int pageNum, int limit, final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMoviePlayed(period, pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMoviePlayedData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        Logger.d("getMoviePlayedData...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadImage(movies, null, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * 最多watch
     */
    public void getMovieWatchedData(String period, int pageNum, int limit, final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieWatched(period, pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieWatchedData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        Logger.d("getMovieWatchedData...onNext:" + movies);
                        callback.onBaseDataSuccess(movies);
                        downloadImage(movies, null, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * 最多收藏
     */
    public void getMovieCollectedData(String period, int pageNum, int limit, final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieCollected(period, pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

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
                        downloadImage(movies, null, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * 暂时不用
     */
    public void getMovieAnticipatedData(int pageNum, int limit, final DataCallback callback) {
        service.getMovieAnticipated(pageNum, limit)
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
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
                        getTmdbImagesByBaseMovie(movies, callback);
                    }
                });
    }

    /**
     * BoxOffice
     */
    public void getMovieBoxOfficeData(final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieBoxOffice()
                .compose(getEachPoster(movies))
                .compose(RxUtil.<List<BaseMovie>>threadSwitch())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

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
                        downloadImage(movies, null, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * 获取列表中各个电影的海报
     */
    private Observable.Transformer<List<BaseMovie>, List<BaseMovie>> getEachPoster(final List<BaseMovie> movies) {
        return new Observable.Transformer<List<BaseMovie>, List<BaseMovie>>() {
            @Override
            public Observable<List<BaseMovie>> call(Observable<List<BaseMovie>> listObservable) {
                return listObservable.flatMap(new Func1<List<BaseMovie>, Observable<BaseMovie>>() {
                    @Override
                    public Observable<BaseMovie> call(List<BaseMovie> baseMovies) {
                        movies.addAll(baseMovies);
                        return Observable.from(movies);
                    }
                }).flatMap(new Func1<BaseMovie, Observable<TmdbMovieImage>>() {
                    @Override
                    public Observable<TmdbMovieImage> call(BaseMovie baseMovie) {
                        return getTmdbMovieImageObservable(baseMovie.getMovie()).subscribeOn(Schedulers.io());
                    }
                }).map(new Func1<TmdbMovieImage, BaseMovie>() {
                    @Override
                    public BaseMovie call(TmdbMovieImage tmdbMovieImage) {
                        return mergeMovieAndImage1(tmdbMovieImage, movies);
                    }
                }).toList();
            }
        };
    }

    /**
     * 从本地or网络获取电影海报
     */
    private Observable<TmdbMovieImage> getTmdbMovieImageObservable(Movie movie) {
        int tmdbId = movie.getIds().getTmdb();
        File file = ImageCacheManager.hasCacheImage(tmdbId, ImageCacheManager.TYPE_POSTER);
        if (file != null) {
            return ImageCacheManager.localPosterImage(tmdbId, file);
        }
        return service.getTmdbImages(tmdbId, BuildConfig.TmdbApiKey);
    }

    /**
     * 从本地or网络获取人物头像
     */
    private Observable<TmdbPersonImage> getTmdbStaffImageObservable(Staff staff) {
        int tmdbId = staff.getPerson().getIds().getTmdb();
        File file = ImageCacheManager.hasCacheImage(tmdbId, ImageCacheManager.TYPE_AVATAR);
        if (file != null) {
            return ImageCacheManager.localAvatarImage(tmdbId, file);
        }
        return service.getPersonImagesFromTmdb(tmdbId, BuildConfig.TmdbApiKey).subscribeOn(Schedulers.io());
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


    /**
     * 获取电影团队
     */
    public Subscription getMovieTeam(String slug, final MovieDetailCallback callback) {
        return service.getMovieTeam(slug)
                .map(new Func1<PeopleCredit, MovieTeam>() {
                    @Override
                    public MovieTeam call(PeopleCredit credit) {
                        // TODO: 2017/1/20 这里去DataMachine里处理一下数据，旨在提取出1个导演3个主演用于MovieDetail显示，剩下一个原始List用于MovieDetail中点击more时使用
                        team = DataMachine.mixingStaffsWorks(credit);
                        return team;
                    }
                }).flatMap(new Func1<MovieTeam, Observable<Staff>>() {
                    @Override
                    public Observable<Staff> call(MovieTeam movieTeam) {
                        return Observable.from(movieTeam.getDetailShowList());
                    }
                }).flatMap(new Func1<Staff, Observable<TmdbPersonImage>>() {
                    @Override
                    public Observable<TmdbPersonImage> call(Staff staff) {
                        return getTmdbStaffImageObservable(staff);
//                        return service.getPersonImagesFromTmdb(staff.getPerson().getIds().getTmdb(),BuildConfig.TmdbApiKey).subscribeOn(Schedulers.io());
                    }
                })
                .compose(RxUtil.<TmdbPersonImage>threadSwitch())
                .subscribe(new Observer<TmdbPersonImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onMovieTeamSuccess(team);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbPersonImage personImage) {
                        List<Staff> detailShowList = team.getDetailShowList();
                        for (Staff staff : detailShowList) {
                            if (staff.getPerson().getIds().getTmdb() == personImage.getId()) {
                                staff.setTmdbPersonImage(personImage);
                                String imageUrl = getImageUrl(staff.getTmdbPersonImage());
                                downloadImage(staff.getTmdbPersonImage(), imageUrl, ImageCacheManager.TYPE_AVATAR);
                            }
                        }
//                        callback.onPeopleSuccess(tmdbPeople);
                    }
                });
    }

    /**
     * 电影其他评分
     */
    public Subscription getMovieOtherRatings(String imdbId, final MovieDetailCallback callback) {
        return service.getMovieOtherRatings(imdbId, "true")
                .compose(RxUtil.<OmdbInfo>threadSwitch())
                .subscribe(new Observer<OmdbInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieImdbRatings...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(OmdbInfo omdbInfo) {
                        callback.onOtherRatingsSuccess(omdbInfo);
                    }
                });
    }

    /******************************************
     * 评论数据
     ********************************************************/

    /**
     * 获取电影评论
     */
    public Subscription getMovieComments(String movieId, String sortCondition, int limit, int page, final MovieDetailCallback movieDetailCallback, final CommentsCallback commentsCallback) {
        Logger.d("加载Comment:" + movieId + "..." + sortCondition + "..." + limit + "..." + page + "..." + movieDetailCallback + "..." + commentsCallback);
        return service.getComments(movieId, sortCondition, limit, page)
                .compose(RxUtil.<List<Comment>>threadSwitch())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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

    /**
     * 获取单个评论的回复
     */
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

    /**
     * 发送针对单个评论的回复
     */
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

    /**
     * 发送针对电影的评论
     */
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

    /**
     * 获取人物电影作品
     */
    public Subscription getStaffMovieCredits(String slug, final DataCallback callback) {
        return service.getStaffMovieCredits(slug)
                .map(new Func1<PeopleCredit, List<Staff>>() {
                    @Override
                    public List<Staff> call(PeopleCredit credits) {
                        return DataMachine.mixingPersonWorks(credits);
                    }
                })
                .compose(RxUtil.<List<Staff>>threadSwitch())
                .subscribe(new Observer<List<Staff>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Staff> works) {
                        callback.onBaseDataSuccess(works);
                    }
                });
    }

    /**
     * 获取人物其他图片
     */
    public Subscription getStaffImages(int tmdbId, final DataCallback callback) {
        return service.getStaffImages(tmdbId, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<StaffImages>threadSwitch())
                .subscribe(new Observer<StaffImages>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(StaffImages staffImages) {
                        callback.onBaseDataSuccess(staffImages);
                    }
                });
    }

    /******************************************
     * OAUTH
     ********************************************************/

    /**
     * 获取AccessToken
     */
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

    /**
     * 获取新AccessToken
     */
    private Observable<? extends TraktToken> getNewAccessToken() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setRefresh_token(SPUtil.getRefreshToken());
        tokenRequest.setClient_id(BuildConfig.TraktID);
        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
        tokenRequest.setGrant_type(Constants.GRANT_TYPE_REFRESH_TOKEN);
        return service.getNewAccessToken(tokenRequest);
    }

    /**
     * 刷新AccessToken
     */
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

    /**
     * 获取UserSettings
     */
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

    /**
     * 获取UserFollowers
     */
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

    /**
     * 获取UserFollowing
     */
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

    /**
     * 获取UserStats
     */
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

    /**
     * 获取电影推荐
     */
    public Subscription getRecommendations(final RecommendationsCallback callback) {
        return service.getRecommendations()
                .onErrorResumeNext(refreshTokenAndRetry(service.getRecommendations()))
                .compose(RxUtil.<List<Movie>>threadSwitch())
                .subscribe(new Observer<List<Movie>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getRecommendations.onError");
                        e.printStackTrace();
                        onError(e);
                    }

                    @Override
                    public void onNext(List<Movie> movies) {
                        getTmdbImagesByMovie(movies, callback);
                    }
                });
    }

    /**
     * 从电影推荐中隐藏不感兴趣的电影
     */
    public Subscription hideMovie(String slug, final RecommendationsCallback callback) {
        return service.hideMovie(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.hideMovie(slug)))
                .compose(RxUtil.threadSwitch())
                .subscribe(new Observer() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onError(e);
                    }

                    @Override
                    public void onNext(Object o) {
                        callback.onHideMovieSuccess();
                    }
                });
    }

    /**
     * 获取用户最后动态时间
     */
    public void getLastActivities() {
        service.getLastActivities()
                .onErrorResumeNext(refreshTokenAndRetry(service.getLastActivities()))
                .compose(RxUtil.<LastActivities>threadSwitch())
                .subscribe(new Observer<LastActivities>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(LastActivities lastActivities) {

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
                            downloadImage(movieImage, imageUrl, ImageCacheManager.TYPE_POSTER);
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
                            downloadImage(movieImage, imageUrl, ImageCacheManager.TYPE_POSTER);
                        }
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

    private String getImageUrl(TmdbPersonImage tmdbPeopleImage) {
        List<TmdbPersonImage.Profiles> profiles = tmdbPeopleImage.getProfiles();
        if (profiles != null && profiles.size() > 0) {
            TmdbPersonImage.Profiles profile = profiles.get(0);
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

    private BaseMovie mergeMovieAndImage1(TmdbMovieImage image, List<BaseMovie> movies) {
        for (BaseMovie movie : movies) {
            if (movie.getMovie().getIds().getTmdb() == image.getId()) {
                getMergedMovie(movie.getMovie(),image);
                return movie;
            }
        }
        return null;
    }

    private Movie mergeMovieAndImage2(TmdbMovieImage image, List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getIds().getTmdb() == image.getId()) {
                return getMergedMovie(movie,image);
            }
        }
        return null;
    }

    public Movie getMergedMovie(Movie movie, TmdbMovieImage image) {
        if (getImageUrl(image) != null) {
            image.setHasPoster(true);
        }
        image.setBitmap(getBitmap(image));
        movie.setImage(image);
        return movie;
    }

    private Bitmap getBitmap(TmdbMovieImage image) {
        Bitmap bitmap = null;
        try {
            if (image != null) {
                if (image.isHasCache()) {
                    bitmap = Glide.with(MoCloud.context).load(image.getCacheFile()).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                } else if (image.isHasPoster()) {
                    String tmdbPosterUrl = StringUtil.buildPosterUrl(image.getPosters().get(0).getFile_path());
                    bitmap = Glide.with(MoCloud.context).load(tmdbPosterUrl).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            return bitmap;
        }
    }

    private void downloadImage(List<BaseMovie> baseMovies, List<Movie> movies, final int imageType) {
        if (baseMovies != null) {
            for (BaseMovie baseMovie : baseMovies) {
                TmdbMovieImage movieImage = baseMovie.getMovie().getImage();
                downloadImage(movieImage, getImageUrl(movieImage), imageType);
            }
        } else {
            for (Movie movie : movies) {
                TmdbMovieImage movieImage = movie.getImage();
                downloadImage(movieImage, getImageUrl(movieImage), imageType);
            }
        }
    }

    private void downloadImage(TmdbImage image, String imageUrl, final int imageType) {
        //imageUrl等于null表示图片已缓存在本地，不需要下载
        if (imageUrl == null) {
            return;
        }
        Logger.d("downLoadImage:hasAvatar:" + image.isHasAvatar());
        if (imageType == ImageCacheManager.TYPE_AVATAR) {
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
