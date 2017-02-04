package com.floatingmuseum.mocloud.data;

import android.Manifest;

import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.Constants;
import com.floatingmuseum.mocloud.data.callback.CommentReplyCallback;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.callback.RecommendationsCallback;
import com.floatingmuseum.mocloud.data.callback.UserDetailCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieImage;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Ratings;
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

    public void getMovieTrendingData(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
        Logger.d("getMovieTrendingData");
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieTrending(pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<TmdbMovieImage>threadSwitch())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieTrendingData...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        Logger.d("getMovieTrendingData...onNext:" + movieImage);
                        handleMoviePoster(movieImage, movies);
                    }
                });
    }

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
        }).compose(RxUtil.<TmdbMovieImage>threadSwitch())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        if (movieImage != null) {
                            mergeMovieAndImage2(movieImage, movies);
                            String imageUrl = getImageUrl(movieImage);
                            downLoadImage(movieImage, imageUrl, ImageCacheManager.TYPE_POSTER);
                        }
                    }
                });
    }

    public void getMoviePlayedData(String period, int pageNum, int limit, final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMoviePlayed(period, pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<TmdbMovieImage>threadSwitch())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        handleMoviePoster(movieImage, movies);
                    }
                });
    }

    public void getMovieWatchedData(String period, int pageNum, int limit, final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieWatched(period, pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<TmdbMovieImage>threadSwitch())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        handleMoviePoster(movieImage, movies);
                    }
                });
    }

    public void getMovieCollectedData(String period, int pageNum, int limit, final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieCollected(period, pageNum, limit)
                .compose(getEachPoster(movies))
                .compose(RxUtil.<TmdbMovieImage>threadSwitch())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        handleMoviePoster(movieImage, movies);
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

    public void getMovieBoxOfficeData(final DataCallback callback) {
        final List<BaseMovie> movies = new ArrayList<>();
        service.getMovieBoxOffice()
                .compose(getEachPoster(movies))
                .compose(RxUtil.<TmdbMovieImage>threadSwitch())
                .subscribe(new Observer<TmdbMovieImage>() {
                    @Override
                    public void onCompleted() {
                        callback.onBaseDataSuccess(movies);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(TmdbMovieImage movieImage) {
                        handleMoviePoster(movieImage, movies);
                    }
                });
    }

    private Observable.Transformer<List<BaseMovie>, TmdbMovieImage> getEachPoster(final List<BaseMovie> movies) {
        return new Observable.Transformer<List<BaseMovie>, TmdbMovieImage>() {
            @Override
            public Observable<TmdbMovieImage> call(Observable<List<BaseMovie>> listObservable) {
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
                });
            }
        };
    }

    private Observable<TmdbMovieImage> getTmdbMovieImageObservable(Movie movie) {
        int tmdbId = movie.getIds().getTmdb();
        File file = ImageCacheManager.hasCacheImage(tmdbId, ImageCacheManager.TYPE_POSTER);
        if (file != null) {
            return ImageCacheManager.localPosterImage(tmdbId, file);
        }
        return service.getTmdbImages(tmdbId, BuildConfig.TmdbApiKey);
    }

    /**
     * 合并图片到集合中，并下载图片
     */
    private void handleMoviePoster(TmdbMovieImage movieImage, List<BaseMovie> movies) {
        if (movieImage != null) {
            mergeMovieAndImage1(movieImage, movies);
            String imageUrl = getImageUrl(movieImage);
            downLoadImage(movieImage, imageUrl, ImageCacheManager.TYPE_POSTER);
        }
    }

    /***************************************************************************************************************/

//    public Subscription getMoviePopular(int pageNum, final DataCallback callback) {
//        Logger.d("测试开始...TmdbMovieDataList");
//        return service.getMoviePopular(pageNum, BuildConfig.TmdbApiKey)
//                .map(RxUtil.checkLocalPosterCache())
//                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
//                .subscribe(new Observer<TmdbMovieDataList>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        callback.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
//                        Logger.d("测试new api...getMoviePopular...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
//                        callback.onBaseDataSuccess(tmdbMovieDataList);
//                        downLoadImage(tmdbMovieDataList, ImageCacheManager.TYPE_POSTER);
//                    }
//                });
//    }

//    public Subscription getMovieNowPlaying(int pagNum, final DataCallback callback) {
//        return service.getMovieNowPlaying(pagNum, BuildConfig.TmdbApiKey)
//                .map(RxUtil.checkLocalPosterCache())
//                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
//                .subscribe(new Observer<TmdbMovieDataList>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        callback.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
//                        Logger.d("测试new api...getMovieNowPlaying...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
//                        callback.onBaseDataSuccess(tmdbMovieDataList);
//                        downLoadImage(tmdbMovieDataList, ImageCacheManager.TYPE_POSTER);
//                    }
//                });
//    }

//    public Subscription getMovieTopRated(int pagNum, final DataCallback callback) {
//        return service.getMovieTopRated(pagNum, BuildConfig.TmdbApiKey)
//                .map(RxUtil.checkLocalPosterCache())
//                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
//                .subscribe(new Observer<TmdbMovieDataList>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        callback.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
//                        Logger.d("测试new api...getMovieTopRated...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
//                        callback.onBaseDataSuccess(tmdbMovieDataList);
//                        downLoadImage(tmdbMovieDataList, ImageCacheManager.TYPE_POSTER);
//                    }
//                });
//    }

//    public Subscription getMovieUpcoming(int pagNum, final DataCallback callback) {
//        return service.getMovieUpcoming(pagNum, BuildConfig.TmdbApiKey)
//                .map(RxUtil.checkLocalPosterCache())
//                .compose(RxUtil.<TmdbMovieDataList>threadSwitch())
//                .subscribe(new Observer<TmdbMovieDataList>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                        callback.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(TmdbMovieDataList tmdbMovieDataList) {
//                        Logger.d("测试new api...getMovieUpcoming...TmdbMovieDataList:" + tmdbMovieDataList.getPage() + "...size:" + tmdbMovieDataList.getResults().size());
//                        callback.onBaseDataSuccess(tmdbMovieDataList);
//                        downLoadImage(tmdbMovieDataList, ImageCacheManager.TYPE_POSTER);
//                    }
//                });
//    }

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

//    public Subscription getMovieDetail(int tmdbId, final MovieDetailCallback callback) {
//        return service.getMovieDetail(tmdbId, BuildConfig.TmdbApiKey, "credits")
//                .compose(RxUtil.<TmdbMovieDetail>threadSwitch())
//                .subscribe(new Observer<TmdbMovieDetail>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Logger.d("getMovieDetail...onError");
//                        e.printStackTrace();
//                        callback.onError(e);
//                    }
//
//                    @Override
//                    public void onNext(TmdbMovieDetail tmdbMovieDetail) {
//                        if (tmdbMovieDetail != null) {
//                            Logger.d("测试new api...TmdbMovieDetail:" + tmdbMovieDetail.getTitle());
//                            callback.onBaseDataSuccess(tmdbMovieDetail);
//                        }
//                    }
//                });
//    }

    public Subscription getMovieTeam(String slug, final MovieDetailCallback callback) {
        return service.getMovieTeam(slug)
                .map(new Func1<People, MovieTeam>() {
                    @Override
                    public MovieTeam call(People people) {
                        // TODO: 2017/1/20 这里去DataMachine里处理一下数据，旨在提取出1个导演3个主演用于MovieDetail显示，剩下一个原始List用于MovieDetail中点击more时使用
                        team = DataMachine.mixingStaffWorks(people);
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
                        return service.getPersonImagesFromTmdb(staff.getPerson().getIds().getTmdb(),BuildConfig.TmdbApiKey).subscribeOn(Schedulers.io());
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
                            if (staff.getPerson().getIds().getTmdb()==personImage.getId()) {
                                staff.setTmdbPersonImage(personImage);
                            }
                        }
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
                        Logger.d("getMovieTraktRatings...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(Ratings ratings) {
                        callback.onTraktRatingsSuccess(ratings);
                    }
                });
    }

    public Subscription getMovieImdbRatings(String imdbId, final MovieDetailCallback callback) {
        return service.getMovieImdbRatings(imdbId)
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
                        callback.onImdbRatingsSuccess(omdbInfo);
                    }
                });
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

    /******************************************
     * 评论数据
     ********************************************************/

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

//    public Subscription getStaffDetail(int tmdbId, final DataCallback callback) {
//        return service.getStaff(tmdbId, BuildConfig.TmdbApiKey)
//                .compose(RxUtil.<TmdbStaff>threadSwitch())
//                .subscribe(new Observer<TmdbStaff>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(TmdbStaff staff) {
//                        callback.onBaseDataSuccess(staff);
//                    }
//                });
//    }

//    public Subscription getStaffMovieCredits(int tmdbId, final DataCallback callback) {
//        return service.getStaffMovieCredits(tmdbId, BuildConfig.TmdbApiKey)
//                .map(new Func1<TmdbStaffMovieCredits, List<Staff>>() {
//                    @Override
//                    public List<Staff> call(TmdbStaffMovieCredits credits) {
//                        return DataMachine.mixingStaffWorks(credits);
//                    }
//                })
//                .compose(RxUtil.<List<Staff>>threadSwitch())
//                .subscribe(new Observer<List<Staff>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        e.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(List<Staff> works) {
//                        callback.onBaseDataSuccess(works);
//                    }
//                });
//    }

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

//    private void downLoadImage(TmdbMovieDataList tmdbMovieDataList, final int imageType) {
//        if (!PermissionsUtil.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Logger.d("没有读写权限，不下载");
//            return;
//        }
//        List<TmdbMovieDetail> movies = tmdbMovieDataList.getResults();
//        if (tmdbMovieDataList != null) {
//            Observable.from(movies)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Action1<TmdbMovieDetail>() {
//                        @Override
//                        public void call(TmdbMovieDetail movie) {
//                            downLoadImage(movie, imageType);
//                        }
//                    });
//        }
//    }

//    private void downLoadImage(final TmdbMovieDetail movie, final int imageType) {
//        File file = movie.getImageCacheFile();
//        if (file != null) {
//            Logger.d("图片下载:...已存在图片" + movie.getTitle());
//            return;
//        }
//        Logger.d("图片下载:...准备下载" + movie.getTitle());
//        String posterUrl = movie.getPoster_path();
//        if (posterUrl != null && posterUrl.length() > 0) {
//            final String fileName = "TMDB-" + movie.getId() + StringUtil.getFileSuffix(posterUrl);
//            String url = StringUtil.buildPosterUrl(posterUrl);
//            service.downloadImage(url)
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Subscriber<ResponseBody>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Logger.d("图片下载:...出错:" + fileName + "..." + movie.getTitle());
//                            e.printStackTrace();
//                        }
//
//                        @Override
//                        public void onNext(ResponseBody responseBody) {
//                            Logger.d("图片下载:...网络获取成功,开始写入磁盘" + movie.getTitle());
//                            ImageCacheManager.writeToDisk(responseBody, fileName, imageType);
//                        }
//                    });
//        }
//    }


    /**
     * 这个download只在Recommendations中使用
     */
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
