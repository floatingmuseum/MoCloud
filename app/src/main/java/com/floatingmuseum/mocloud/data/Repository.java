package com.floatingmuseum.mocloud.data;

import android.Manifest;
import android.text.TextUtils;

import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.Constants;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.callback.RecommendationsCallback;
import com.floatingmuseum.mocloud.data.callback.StaffWorksCallback;
import com.floatingmuseum.mocloud.data.callback.SyncCallback;
import com.floatingmuseum.mocloud.data.callback.UserDetailCallback;
import com.floatingmuseum.mocloud.data.db.RealmManager;
import com.floatingmuseum.mocloud.data.db.entity.RealmCommentLike;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieCollection;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatched;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatchlist;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.FeatureList;
import com.floatingmuseum.mocloud.data.entity.FeatureListItem;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.MovieSyncItem;
import com.floatingmuseum.mocloud.data.entity.SyncData;
import com.floatingmuseum.mocloud.data.entity.LastActivities;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieCollectionItem;
import com.floatingmuseum.mocloud.data.entity.MovieRatingItem;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchlistItem;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.StaffImages;
import com.floatingmuseum.mocloud.data.entity.Stats;
import com.floatingmuseum.mocloud.data.entity.SyncResponse;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPersonImage;
import com.floatingmuseum.mocloud.data.entity.TokenRequest;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.data.entity.UserCommentLike;
import com.floatingmuseum.mocloud.data.entity.UserListLike;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.floatingmuseum.mocloud.utils.ErrorUtil;
import com.floatingmuseum.mocloud.utils.ListUtil;
import com.floatingmuseum.mocloud.utils.PermissionsUtil;
import com.floatingmuseum.mocloud.utils.RxUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import io.reactivex.disposables.Disposable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * Created by Floatingmuseum on 2016/7/11.
 */

public class Repository {

    private static Repository repository;
    private MoCloudService service;

    public Repository() {
        service = MoCloudFactory.getInstance();
    }

    public static void init() {
        repository = new Repository();
    }

    public static Repository getInstance() {
        return repository;
    }

    /**
     * 获取列表中各个电影的海报
     */
    protected ObservableTransformer<List<BaseMovie>, List<BaseMovie>> getEachPoster(final List<BaseMovie> movies, final int type) {
        return new ObservableTransformer<List<BaseMovie>, List<BaseMovie>>() {
            @Override
            public ObservableSource<List<BaseMovie>> apply(Observable<List<BaseMovie>> upstream) {
                return upstream.flatMap(new Function<List<BaseMovie>, ObservableSource<BaseMovie>>() {
                    @Override
                    public ObservableSource<BaseMovie> apply(List<BaseMovie> baseMovies) throws Exception {
                        movies.addAll(baseMovies);
                        return Observable.fromIterable(movies);
                    }
                })
                        .flatMap(new Function<BaseMovie, ObservableSource<ArtImage>>() {
                            @Override
                            public ObservableSource<ArtImage> apply(BaseMovie baseMovie) throws Exception {
                                return getArtImageObservable(baseMovie.getMovie().getIds().getTmdb(), type).subscribeOn(Schedulers.io());
                            }
                        })
                        .map(new Function<ArtImage, BaseMovie>() {
                            @Override
                            public BaseMovie apply(ArtImage image) throws Exception {
                                return mergeMovieAndImage1(image, movies, type);

                            }
                        })
                        .toList()
                        .toObservable();
            }
        };
    }

    private Observable<ArtImage> getArtImageObservable(final int tmdbID, final int type) {
        File file = ImageCacheManager.hasCacheImage(tmdbID, type);
        if (file != null) {
            return ImageCacheManager.localArtImage(tmdbID, file, type);
        } else {
            final long startTime = System.currentTimeMillis();
            return service.getTmdbImages(tmdbID, BuildConfig.TmdbApiKey)
                    .onErrorReturn(new Function<Throwable, TmdbMovieImage>() {
                        @Override
                        public TmdbMovieImage apply(Throwable throwable) throws Exception {
                            Logger.d("getTmdbImages...onErrorReturn");
                            throwable.printStackTrace();
                            TmdbMovieImage tmdbMovieImage = new TmdbMovieImage();
                            tmdbMovieImage.setId(tmdbID);
                            return tmdbMovieImage;
                        }
                    }).map(new Function<TmdbMovieImage, ArtImage>() {
                        @Override
                        public ArtImage apply(TmdbMovieImage tmdbMovieImage) throws Exception {
                            ArtImage image = new ArtImage();
                            image.setTmdbID(tmdbMovieImage.getId());
                            if (ImageCacheManager.TYPE_POSTER == type) {
                                getPosterImageUrl(image, tmdbMovieImage.getPosters());
                            } else {
                                getBackdropImageUrl(image, tmdbMovieImage.getBackdrops());
                            }
                            Logger.d("获取网络图片耗时:...TMDB-ID:" + tmdbID + "..." + (System.currentTimeMillis() - startTime));
                            return image;
                        }
                    });
        }
    }

    /**
     * 从本地or网络获取电影海报
     */
    protected Observable<ArtImage> getTmdbMovieImageObservable(final Movie movie) {
        Logger.d("getTmdbMovieImageObservable...Movie:" + movie.getTitle() + "...TmdbId:" + movie.getIds().getTmdb());
        int tmdbId = movie.getIds().getTmdb();
        File file = ImageCacheManager.hasCacheImage(tmdbId, ImageCacheManager.TYPE_POSTER);
        if (file != null) {
            return ImageCacheManager.localArtImage(tmdbId, file, ImageCacheManager.TYPE_POSTER);
        }
        return service.getTmdbImages(tmdbId, BuildConfig.TmdbApiKey)
                .onErrorReturn(new Function<Throwable, TmdbMovieImage>() {
                    @Override
                    public TmdbMovieImage apply(Throwable throwable) {
                        Logger.d("getTmdbImages...onErrorReturn");
                        throwable.printStackTrace();
                        TmdbMovieImage tmdbMovieImage = new TmdbMovieImage();
                        tmdbMovieImage.setId(movie.getIds().getTmdb());
                        return tmdbMovieImage;
                    }
                }).map(new Function<TmdbMovieImage, ArtImage>() {
                    @Override
                    public ArtImage apply(TmdbMovieImage tmdbMovieImage) {
                        ArtImage image = new ArtImage();
                        image.setTmdbID(tmdbMovieImage.getId());
                        if (ListUtil.hasData(tmdbMovieImage.getPosters())) {
//                            image.setRemoteImageUrl(getImageUrl(tmdbMovieImage));
                        }
                        return image;
                    }
                });
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

    public Disposable getMovieDetail(String movieId, final MovieDetailCallback<Movie> callback) {
//        return service.getMovieDetail(movieId)
//                .compose(RxUtil.<Movie>observableTransformer()())
//                .subscribe(new Consumer<Movie>() {
//                    @Override
//                    public void accept(Movie movie) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });

        return null;
    }


    /**
     * 获取电影团队
     */
    public Disposable getMovieTeam(String slug, final MovieDetailCallback callback) {
        final MovieTeam team = new MovieTeam();
        return service.getMovieTeam(slug)
                .map(new Function<PeopleCredit, MovieTeam>() {
                    @Override
                    public MovieTeam apply(PeopleCredit credit) {
                        //这里去DataMachine里处理一下数据，旨在提取出1个导演3个主演用于MovieDetail显示，剩下一个原始List用于MovieDetail中点击more时使用
                        MovieTeam tempTeam = DataMachine.mixingStaffsWorks(credit);
                        team.setDetailShowList(tempTeam.getDetailShowList());
                        team.setPeopleCredit(credit);
                        return team;
                    }
                })
                .flatMap(new Function<MovieTeam, ObservableSource<Staff>>() {
                    @Override
                    public ObservableSource<Staff> apply(MovieTeam movieTeam) throws Exception {
                        return Observable.fromIterable(movieTeam.getDetailShowList());

                    }
                })
                .flatMap(new Function<Staff, ObservableSource<TmdbPersonImage>>() {
                    @Override
                    public Observable<TmdbPersonImage> apply(Staff staff) {
                        return getTmdbStaffImageObservable(staff);
                    }
                })
                .map(new Function<TmdbPersonImage, Staff>() {
                    @Override
                    public Staff apply(TmdbPersonImage tmdbPersonImage) {
                        List<Staff> detailShowList = team.getDetailShowList();
                        for (Staff staff : detailShowList) {
                            if (staff.getPerson().getIds().getTmdb() == tmdbPersonImage.getId()) {
                                if (getImageUrl(staff.getTmdbPersonImage()) != null) {
                                    tmdbPersonImage.setHasAvatar(true);
                                }
                                staff.setTmdbPersonImage(tmdbPersonImage);
                                return staff;
                            }
                        }
                        return null;
                    }
                })
                .toList()
                .map(new Function<List<Staff>, List<Staff>>() {
                    @Override
                    public List<Staff> apply(List<Staff> staffs) {
                        //因为上面getTmdbStaffImageObservable方法中4个人物获取图片的线程是同时运行的，所以返回的数据可能与之前的顺序不同，需要将导演放到第一位(如果有导演的话)
                        Staff director = null;
                        for (Staff staff : staffs) {
                            if ("Director".equals(staff.getJob())) {
                                director = staff;
                            }
                        }

                        if (director != null) {
                            if (staffs.indexOf(director) != 0) {
                                staffs.remove(director);
                                staffs.add(0, director);
                            }
                        }
                        return staffs;
                    }
                })
                .compose(RxUtil.<List<Staff>>singleTransformer())
                .subscribe(new Consumer<List<Staff>>() {
                    @Override
                    public void accept(List<Staff> staffs) throws Exception {
                        // TODO: 2017/2/13 confused here.
                        /*
                          这里按理说上面的对staff的操作可以直接影响MovieTeam里的staff对象，大多数结果也的确如此
                          但是电影Live by night中，导演和主演都是本阿弗莱克。结果导演本有TmdbPersonImage，演员本就没有TmdbPersonImage。
                          而且只是演员本没有，其他演员仍然有TmdbPersonImage。
                          所以之后再set一次staffs到MovieTeam中
                         */
                        team.setDetailShowList(staffs);
                        callback.onMovieTeamSuccess(team);
                        downloadPersonImage(staffs, ImageCacheManager.TYPE_AVATAR);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("getMovieTeam...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /**
     * 电影其他评分
     */
    public Disposable getMovieOtherRatings(String imdbID, final MovieDetailCallback callback) {
//        return service.getMovieOtherRatings(imdbID, "true")
//                .compose(RxUtil.<OmdbInfo>observableTransformer()())
//                .subscribe(new Consumer<OmdbInfo>() {
//                    @Override
//                    public void accept(OmdbInfo omdbInfo) throws Exception {
//                        callback.onOtherRatingsSuccess(omdbInfo);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable e) throws Exception {
//                        Logger.d("getMovieImdbRatings...onError");
//                        e.printStackTrace();
//                        callback.onError(e);
//                    }
//                });

        return null;
    }

    /******************************************
     * 评论数据
     ********************************************************/

    /**
     * 获取电影评论
     */
    public Disposable getMovieComments(String movieId, String sortCondition, int limit, int page, final MovieDetailCallback movieDetailCallback, final CommentsCallback<List<Comment>> commentsCallback) {
        Logger.d("加载Comment:" + movieId + "..." + sortCondition + "..." + limit + "..." + page + "..." + movieDetailCallback + "..." + commentsCallback);
        return service.getComments(movieId, sortCondition, limit, page)
                .doOnNext(RxUtil.updateCommentsResultLikesState())
                .compose(RxUtil.<List<Comment>>observableTransformer())
                .subscribe(new Consumer<List<Comment>>() {
                    @Override
                    public void accept(List<Comment> comments) throws Exception {
                        if (movieDetailCallback != null) {
                            movieDetailCallback.onCommentsSuccess(comments);
                        } else {
                            commentsCallback.onBaseDataSuccess(comments);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        e.printStackTrace();
                        if (movieDetailCallback != null) {
                            movieDetailCallback.onError(e);
                        } else {
                            commentsCallback.onError(e);
                        }
                    }
                });
    }

    /**
     * 获取单个评论的回复
     */
    public Disposable getCommentReplies(long commentId, final DataCallback<List<Comment>> callback) {
        return service.getCommentReplies(commentId)
                .doOnNext(RxUtil.updateCommentsResultLikesState())
                .compose(RxUtil.<List<Comment>>observableTransformer())
                .subscribe(new Consumer<List<Comment>>() {
                    @Override
                    public void accept(List<Comment> comments) throws Exception {
                        Logger.d("getCommentReplies...onNext");
                        callback.onBaseDataSuccess(comments);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("getCommentReplies...onError");
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 发送针对单个评论的回复
     */
    public Disposable sendReply(long id, Reply reply, final CommentsCallback callback) {
        Logger.d("sendReply:" + id + "..." + reply.getComment());
        return service.sendReply(id, reply)
                .onErrorResumeNext(refreshTokenAndRetry(service.sendReply(id, reply)))
                .compose(RxUtil.<Comment>observableTransformer())
                .subscribe(new Consumer<Comment>() {
                    @Override
                    public void accept(Comment comment) throws Exception {
                        callback.onSendCommentSuccess(comment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("sendReply...onError");
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 发送针对电影的评论
     */
    public Disposable sendComment(final Comment comment, String imdb_id, final CommentsCallback callback) {
        // TODO: 2017/1/3 comment在设置了movie后，在refresh方法中的comment理论上应该也是带有movie对象的，待测试 
        return service.getMovieDetail(imdb_id).flatMap(new Function<Movie, Observable<Comment>>() {
            @Override
            public Observable<Comment> apply(Movie movie) {
                comment.setMovie(movie);
                return service.sendComment(comment);
            }
        })
                .onErrorResumeNext(refreshTokenAndRetry(service.sendComment(comment)))
                .compose(RxUtil.<Comment>observableTransformer())
                .subscribe(new Consumer<Comment>() {
                    @Override
                    public void accept(Comment comment) throws Exception {
                        callback.onSendCommentSuccess(comment);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    public void addCommentToLikes(final Comment comment, final CommentsCallback callback) {
        service.addCommentToLikes(comment.getId())
                .onErrorResumeNext(refreshTokenAndRetry(service.addCommentToLikes(comment.getId())))
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) {
                        RealmCommentLike realmCommentLike = new RealmCommentLike();
                        User user = comment.getUser();
                        realmCommentLike.setLiked_at(TimeUtil.getNowUTCTime());
                        realmCommentLike.setId(comment.getId());
                        realmCommentLike.setParent_id(comment.getParent_id());
                        realmCommentLike.setCreated_at(comment.getCreated_at());
                        realmCommentLike.setUpdated_at(comment.getUpdated_at());
                        realmCommentLike.setComment(comment.getComment());
                        realmCommentLike.setSpoiler(comment.isSpoiler());
                        realmCommentLike.setReview(comment.isReview());
                        realmCommentLike.setReplies(comment.getReplies());
                        realmCommentLike.setLikes(comment.getLikes());
                        realmCommentLike.setUser_rating(comment.getUser_rating());
                        realmCommentLike.setUsername(user.getUsername());

                        realmCommentLike.setPrivate_user(user.isPrivateX());
                        realmCommentLike.setUser_slug(user.getIds().getSlug());
                        realmCommentLike.setVip(user.isVip());
                        realmCommentLike.setVip_ep(user.isVip_ep());
                        realmCommentLike.setName(user.getName());
                        RealmManager.insertOrUpdate(realmCommentLike);
                    }
                })
                .compose(RxUtil.<ResponseBody>observableTransformer())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Logger.d("评论点赞测试:addCommentToLikes...点赞成功");
                        callback.onAddCommentToLikesSuccess(comment.getId());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("评论点赞测试:addCommentToLikes...点赞失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    public void removeCommentFromLikes(final Comment comment, final CommentsCallback callback) {
        service.removeCommentFromLikes(comment.getId())
                .onErrorResumeNext(refreshTokenAndRetry(service.removeCommentFromLikes(comment.getId())))
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        RealmManager.delete(RealmCommentLike.class, "id", comment.getId());
                    }
                })
                .compose(RxUtil.<ResponseBody>observableTransformer())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        Logger.d("评论点赞测试:removeCommentFromLikes...取消点赞成功");
                        callback.onRemoveCommentFromLikesSuccess(comment.getId());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("评论点赞测试:removeCommentFromLikes...取消点赞失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /******************************************
     * 影人数据
     ********************************************************/

    /**
     * 获取人物电影作品
     */
    public Disposable getStaffMovieCredits(int traktId, final DataCallback<List<Staff>> callback) {
        return service.getStaffMovieCredits(traktId)
                .map(new Function<PeopleCredit, List<Staff>>() {
                    @Override
                    public List<Staff> apply(PeopleCredit credits) {
                        return DataMachine.mixingPersonWorks(credits);
                    }
                })
                .compose(RxUtil.<List<Staff>>observableTransformer())
                .subscribe(new Consumer<List<Staff>>() {
                    @Override
                    public void accept(List<Staff> works) throws Exception {
                        callback.onBaseDataSuccess(works);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public Disposable getWorksImages(final List<Staff> subWorks, final StaffWorksCallback callback) {
        return Observable.fromIterable(subWorks)
                .flatMap(new Function<Staff, Observable<ArtImage>>() {
                    @Override
                    public Observable<ArtImage> apply(Staff staff) {
                        Logger.d("getWorksImages...getImage");
//                        return getTmdbMovieImageObservable(staff.getMovie());
                        return getArtImageObservable(staff.getMovie().getIds().getTmdb(), ImageCacheManager.TYPE_POSTER);
                    }
                }).map(new Function<ArtImage, Staff>() {
                    @Override
                    public Staff apply(ArtImage image) {
                        Logger.d("getWorksImages...merge..." + image);
                        for (Staff staff : subWorks) {
                            Movie movie = staff.getMovie();
                            if (movie.getIds().getTmdb() == image.getTmdbID()) {
                                Logger.d("getWorksImages...movieId:" + movie.getIds().getTmdb() + "...imageId:" + image.getTmdbID());
                                staff.setMovie(getMergedMovie(movie, image, ImageCacheManager.TYPE_AVATAR));
                                return staff;
                            }
                        }
                        return null;
                    }
                }).toList()
                .compose(RxUtil.<List<Staff>>singleTransformer())
                .subscribe(new Consumer<List<Staff>>() {
                    @Override
                    public void accept(List<Staff> staffs) throws Exception {
                        callback.onGetWorksImagesSuccess(staffs);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("getWorksImages...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /**
     * 获取人物其他图片
     */
    public Disposable getStaffImages(int tmdbId, final DataCallback<StaffImages> callback) {
        return service.getStaffImages(tmdbId, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<StaffImages>observableTransformer())
                .subscribe(new Consumer<StaffImages>() {
                    @Override
                    public void accept(StaffImages staffImages) throws Exception {
                        callback.onBaseDataSuccess(staffImages);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /******************************************
     * OAUTH
     ********************************************************/

    /**
     * 获取AccessToken
     */
    public void getAccessToken(String code, final DataCallback<TraktToken> dataCallback) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setCode(code);
        tokenRequest.setClient_id(BuildConfig.TraktID);
        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
        tokenRequest.setGrant_type(Constants.GRANT_TYPE_AUTHORIZATION_CODE);

        service.getToken(tokenRequest)
                .compose(RxUtil.<TraktToken>observableTransformer())
                .subscribe(new Consumer<TraktToken>() {
                    @Override
                    public void accept(TraktToken traktToken) throws Exception {
                        Logger.d("请求成功");
                        dataCallback.onBaseDataSuccess(traktToken);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("请求失败");
                        dataCallback.onError(e);
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
    private <T> Function<Throwable, ? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> tobeResumed) {
        return new Function<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> apply(Throwable throwable) {
                Logger.d("refreshTokenAndRetry:出现异常");
                if (ErrorUtil.is401Error(throwable)) {
                    Logger.d("refreshTokenAndRetry:401异常");
                    return getNewAccessToken().flatMap(new Function<TraktToken, Observable<T>>() {
                        @Override
                        public Observable<T> apply(TraktToken traktToken) {
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
     * **********************************************************
     * <p>
     * /**
     * 获取UserFollowers
     */
    public Disposable getUserFollowers(String slug, final UserDetailCallback callback) {
        return service.getUserFollowers(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserFollowers(slug)))
                .compose(RxUtil.<List<Follower>>observableTransformer())
                .subscribe(new Consumer<List<Follower>>() {
                    @Override
                    public void accept(List<Follower> followers) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("UserData:getUserFollowers...onError");
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 获取UserFollowing
     */
    public Disposable getUserFollowing(String slug, final UserDetailCallback callback) {
        return service.getUserFollowing(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserFollowing(slug)))
                .compose(RxUtil.<List<Follower>>observableTransformer())
                .subscribe(new Consumer<List<Follower>>() {
                    @Override
                    public void accept(List<Follower> followers) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("UserData:getUserFollowing...onError");
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 获取UserStats
     */
    public Disposable getUserStats(String slug, final UserDetailCallback callback) {
        return service.getUserStats(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.getUserStats(slug)))
                .compose(RxUtil.<Stats>observableTransformer())
                .subscribe(new Consumer<Stats>() {
                    @Override
                    public void accept(Stats stats) throws Exception {
                        if (stats != null) {
                            Logger.d("UserData:getUserStats...onNext:" + stats.getMovies().getMinutes());
                        }
                        callback.onUserStatsSuccess(stats);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("UserData:getUserStats...onError");
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 获取当前用户推荐
     */
    public Disposable getRecommendations(final RecommendationsCallback<List<Movie>> callback) {
        final List<Movie> movies = new ArrayList<>();
        return service.getRecommendations()
                .onErrorResumeNext(refreshTokenAndRetry(service.getRecommendations()))
                .flatMap(new Function<List<Movie>, Observable<Movie>>() {
                    @Override
                    public Observable<Movie> apply(List<Movie> movieData) {
                        movies.addAll(movieData);
                        return Observable.fromIterable(movies);
                    }
                })
                .flatMap(new Function<Movie, Observable<ArtImage>>() {
                    @Override
                    public Observable<ArtImage> apply(Movie movie) {
                        return getArtImageObservable(movie.getIds().getTmdb(), ImageCacheManager.TYPE_POSTER);
                    }
                })
                .map(new Function<ArtImage, Movie>() {
                    @Override
                    public Movie apply(ArtImage image) {
                        return mergeMovieAndImage2(image, movies, ImageCacheManager.TYPE_POSTER);
                    }
                })
                .toList()
                .compose(RxUtil.<List<Movie>>singleTransformer())
                .subscribe(new Consumer<List<Movie>>() {
                    @Override
                    public void accept(List<Movie> movies) throws Exception {
                        callback.onBaseDataSuccess(movies);
                        for (Movie movie : movies) {
                            downloadImage(movie.getIds().getTmdb(), movie.getImage().getRemotePosterUrl(), ImageCacheManager.TYPE_POSTER);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    public Disposable getFeatureList(final String userID, final String listID, final RecommendationsCallback callback) {
        return service.getFeatureList(userID, listID)
                .onErrorResumeNext(refreshTokenAndRetry(service.getFeatureList(userID, listID)))
                .compose(RxUtil.<FeatureList>observableTransformer())
                .subscribe(new Consumer<FeatureList>() {
                    @Override
                    public void accept(FeatureList featureList) throws Exception {
                        Logger.d("FeatureList...onNext:" + userID + "..." + listID);
                        callback.onGetFeatureListSuccess(featureList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("FeatureList...onError:" + userID + "..." + listID);
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    public Disposable getFeatureListData(final String userID, final String listID, final RecommendationsCallback callback) {
        return service.getFeatureListData(userID, listID)
                .onErrorResumeNext(refreshTokenAndRetry(service.getFeatureListData(userID, listID)))
                .compose(RxUtil.<List<FeatureListItem>>observableTransformer())
                .subscribe(new Consumer<List<FeatureListItem>>() {
                    @Override
                    public void accept(List<FeatureListItem> featureListItems) throws Exception {
                        Logger.d("FeatureList...onNext:" + userID + "..." + listID);
                        callback.onGetFeatureListDataSuccess(listID, featureListItems);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("FeatureList...onError:" + userID + "..." + listID);
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /**
     * 从电影推荐中隐藏不感兴趣的电影
     */
    public Disposable hideMovie(String slug, final RecommendationsCallback callback) {
        return service.hideMovie(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.hideMovie(slug)))
                .compose(RxUtil.<ResponseBody>observableTransformer())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        callback.onHideMovieSuccess();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /**
     * 获取用户最后动态时间
     */
    void getLastActivities(final SyncCallback callback) {
        service.getLastActivities()
                .onErrorResumeNext(refreshTokenAndRetry(service.getLastActivities()))
                .compose(RxUtil.<LastActivities>observableTransformer())
                .subscribe(new Consumer<LastActivities>() {
                    @Override
                    public void accept(LastActivities lastActivities) throws Exception {
                        callback.onLastActivitiesSuccess(lastActivities);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:getLastActivities...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /**
     * 获取UserSettings
     */
    public Disposable getUserSettings(final DataCallback<UserSettings> callback) {
        return service.syncUserSettings()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserSettings()))
                .compose(RxUtil.<UserSettings>observableTransformer())
                .subscribe(new Consumer<UserSettings>() {
                    @Override
                    public void accept(UserSettings userSettings) throws Exception {
                        Logger.d("UserSettings:onNext:" + userSettings);
                        callback.onBaseDataSuccess(userSettings);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("UserSettings:onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    /**
     * 获取UserSettings
     */
    Disposable syncUserSettings(final SyncCallback callback) {
        return service.syncUserSettings()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserSettings()))
                .compose(RxUtil.<UserSettings>observableTransformer())
                .subscribe(new Consumer<UserSettings>() {
                    @Override
                    public void accept(UserSettings userSettings) throws Exception {
                        Logger.d("UserSettings:onNext:" + userSettings);
                        callback.onSyncUserSettingsSuccess(userSettings);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncUserSettings...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    void syncMovieWatched(final SyncCallback callback) {
        service.syncMovieWatched()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMovieWatched()))
                .doOnNext(new Consumer<List<MovieWatchedItem>>() {
                    @Override
                    public void accept(List<MovieWatchedItem> movieWatchedItems) {
                        Logger.d("Sync数据:看过:" + movieWatchedItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieWatched(movieWatchedItems);
                    }
                })
                .compose(RxUtil.<List<MovieWatchedItem>>observableTransformer())
                .subscribe(new Consumer<List<MovieWatchedItem>>() {
                    @Override
                    public void accept(List<MovieWatchedItem> movieWatchedItems) throws Exception {
                        callback.onSyncMovieWatchedSuccess(movieWatchedItems);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncMovieWatched...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    public void addMovieToWatched(final SyncData item, final MovieDetailCallback callback) {
        service.addMovieToWatched(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.addMovieToWatched(item)))
                .doOnNext(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) {
                        RealmMovieWatched realmMovieWatched = new RealmMovieWatched();
                        MovieSyncItem movieSyncItem = item.getMovies().get(0);
                        realmMovieWatched.setTitle(movieSyncItem.getTitle());
                        realmMovieWatched.setYear(movieSyncItem.getYear());
                        realmMovieWatched.setTrakt_id(movieSyncItem.getIds().getTrakt());
                        realmMovieWatched.setLast_watched_at(movieSyncItem.getWatched_at());
                        RealmManager.insertOrUpdate(realmMovieWatched);
                        //当一个处于想看列表的电影，被点击看过之后，要移除其在数据库想看表中的数据
                        RealmManager.delete(RealmMovieWatchlist.class, "trakt_id", movieSyncItem.getIds().getTrakt());
                    }
                })
                .compose(RxUtil.<SyncResponse>observableTransformer())
                .subscribe(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) throws Exception {
                        Logger.d("看过测试:addMovieToWatched:...add成功");
                        callback.onAddMovieToWatchedSuccess(syncResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("看过测试:addMovieToWatched:...add失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    public void removeMovieFromWatched(final SyncData item, final MovieDetailCallback callback) {
        service.removeMovieFromWatched(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.removeMovieFromWatched(item)))
                .doOnNext(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) {
                        RealmManager.delete(RealmMovieWatched.class, "trakt_id", item.getMovies().get(0).getIds().getTrakt());
                    }
                })
                .compose(RxUtil.<SyncResponse>observableTransformer())
                .subscribe(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) throws Exception {
                        Logger.d("看过测试:addMovieToWatched:...remove成功..." + syncResponse.getDeleted().getMovies());
                        callback.onRemoveMovieFromWatchedSuccess(syncResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("看过测试:addMovieToWatched:...remove失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    void syncMovieWatchlist(final SyncCallback callback) {
        service.syncMovieWatchlist()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMovieWatchlist()))
                .doOnNext(new Consumer<List<MovieWatchlistItem>>() {
                    @Override
                    public void accept(List<MovieWatchlistItem> movieWatchlistItems) {
                        Logger.d("Sync数据:想看:" + movieWatchlistItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieWatchlist(movieWatchlistItems);
                    }
                })
                .compose(RxUtil.<List<MovieWatchlistItem>>observableTransformer())
                .subscribe(new Consumer<List<MovieWatchlistItem>>() {
                    @Override
                    public void accept(List<MovieWatchlistItem> movieWatchlistItems) throws Exception {
                        callback.onSyncMovieWatchlistSuccess(movieWatchlistItems);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncMovieWatchlist...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    public void addMovieToWatchlist(final SyncData item, final MovieDetailCallback callback) {
        service.addMovieToWatchlist(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.addMovieToWatchlist(item)))
                .doOnNext(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) {
                        RealmMovieWatchlist realmMovieWatchlist = new RealmMovieWatchlist();
                        realmMovieWatchlist.setTitle(item.getMovies().get(0).getTitle());
                        realmMovieWatchlist.setYear(item.getMovies().get(0).getYear());
                        realmMovieWatchlist.setTrakt_id(item.getMovies().get(0).getIds().getTrakt());
                        realmMovieWatchlist.setListed_at(item.getMovies().get(0).getListed_at());
                        RealmManager.insertOrUpdate(realmMovieWatchlist);
                    }
                })
                .compose(RxUtil.<SyncResponse>observableTransformer())
                .subscribe(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) throws Exception {
                        Logger.d("想看测试:addMovieToWatchlist:...add成功..." + syncResponse.getAdded().getMovies());
                        callback.onAddMovieToWatchlistSuccess(syncResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("想看测试:addMovieToWatchlist:...add失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    public void removeMovieFromWatchlist(final SyncData item, final MovieDetailCallback callback) {
        service.removeMovieFromWatchlist(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.removeMovieFromWatchlist(item)))
                .doOnNext(new Consumer<SyncResponse>() {

                    @Override
                    public void accept(SyncResponse syncResponse) {
                        RealmManager.delete(RealmMovieWatchlist.class, "trakt_id", item.getMovies().get(0).getIds().getTrakt());
                    }
                })
                .compose(RxUtil.<SyncResponse>observableTransformer())
                .subscribe(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) throws Exception {
                        Logger.d("想看测试:removeMovieFromWatchlist:...remove成功..." + syncResponse.getDeleted().getMovies());
                        callback.onRemoveMovieFromWatchlistSuccess(syncResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("想看测试:removeMovieFromWatchlist:...remove失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    void syncMovieRatings(final SyncCallback callback) {
        service.syncMovieRatings()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMovieRatings()))
                .doOnNext(new Consumer<List<MovieRatingItem>>() {
                    @Override
                    public void accept(List<MovieRatingItem> movieRatingItems) {
                        Logger.d("Sync数据:评分:" + movieRatingItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieRating(movieRatingItems);
                    }
                })
                .compose(RxUtil.<List<MovieRatingItem>>observableTransformer())
                .subscribe(new Consumer<List<MovieRatingItem>>() {
                    @Override
                    public void accept(List<MovieRatingItem> movieRatingItems) throws Exception {
                        callback.onSyncMovieRatingsSuccess(movieRatingItems);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncMovieRatings...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    void syncMovieCollection(final SyncCallback callback) {
        service.syncMoviesCollections()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMoviesCollections()))
                .doOnNext(new Consumer<List<MovieCollectionItem>>() {
                    @Override
                    public void accept(List<MovieCollectionItem> movieCollectionItems) {
                        Logger.d("Sync数据:收藏:" + movieCollectionItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieCollection(movieCollectionItems);
                    }
                })
                .compose(RxUtil.<List<MovieCollectionItem>>observableTransformer())
                .subscribe(new Consumer<List<MovieCollectionItem>>() {
                    @Override
                    public void accept(List<MovieCollectionItem> movieCollectionItems) throws Exception {
                        callback.onSyncMovieCollectionSuccess(movieCollectionItems);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncMovieCollection...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    public void addMovieToCollection(final SyncData item, final MovieDetailCallback callback) {
        // TODO: 2017/3/3 add various metadata
        service.addMovieToCollection(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.addMovieToCollection(item)))
                .doOnNext(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) {
                        RealmMovieCollection realmMovieCollection = new RealmMovieCollection();
                        realmMovieCollection.setTitle(item.getMovies().get(0).getTitle());
                        realmMovieCollection.setYear(item.getMovies().get(0).getYear());
                        realmMovieCollection.setTrakt_id(item.getMovies().get(0).getIds().getTrakt());
                        realmMovieCollection.setCollected_at(item.getMovies().get(0).getCollected_at());
                        RealmManager.insertOrUpdate(realmMovieCollection);
                    }
                })
                .compose(RxUtil.<SyncResponse>observableTransformer())
                .subscribe(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) throws Exception {
                        Logger.d("收藏测试:addMovieToCollection:...add成功..." + syncResponse.getAdded().getMovies());
                        callback.onAddMovieToCollectionSuccess(syncResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("收藏测试:addMovieToCollection:...add");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    public void removeMovieFromCollection(final SyncData item, final MovieDetailCallback callback) {
        service.removeMovieFromCollection(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.removeMovieFromCollection(item)))
                .doOnNext(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) {
                        RealmManager.delete(RealmMovieCollection.class, "trakt_id", item.getMovies().get(0).getIds().getTrakt());
                    }
                })
                .compose(RxUtil.<SyncResponse>observableTransformer())
                .subscribe(new Consumer<SyncResponse>() {
                    @Override
                    public void accept(SyncResponse syncResponse) throws Exception {
                        Logger.d("收藏测试:removeMovieFromCollection:...remove成功..." + syncResponse.getDeleted().getMovies());
                        callback.onRemoveMovieFromCollectionSuccess(syncResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("收藏测试:removeMovieFromCollection:...remove失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }
                });
    }

    void syncUserCommentsLikes(final SyncCallback callback) {
        service.syncUserCommentsLikes()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserCommentsLikes()))
                .doOnNext(new Consumer<List<UserCommentLike>>() {
                    @Override
                    public void accept(List<UserCommentLike> userCommentLikes) {
                        Logger.d("Sync数据:评论点赞:" + userCommentLikes.size());
                        RealmManager.insertOrUpdateUserCommentsLikes(userCommentLikes);
                    }
                })
                .compose(RxUtil.<List<UserCommentLike>>observableTransformer())
                .subscribe(new Consumer<List<UserCommentLike>>() {
                    @Override
                    public void accept(List<UserCommentLike> userCommentLikes) throws Exception {
                        callback.onSyncUserCommentsLikesSuccess(userCommentLikes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncUserCommentsLikes...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    void syncUserListsLikes(final SyncCallback callback) {
        service.syncUserListsLikes()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserListsLikes()))
                .doOnNext(new Consumer<List<UserListLike>>() {
                    @Override
                    public void accept(List<UserListLike> userListLikes) {
                        Logger.d("Sync数据:列表点赞:" + userListLikes.size());
                        RealmManager.insertOrUpdateUserListsLikes(userListLikes);
                    }
                })
                .compose(RxUtil.<List<UserListLike>>observableTransformer())
                .subscribe(new Consumer<List<UserListLike>>() {
                    @Override
                    public void accept(List<UserListLike> userListLikes) throws Exception {
                        callback.onSyncUserListLikesSuccess(userListLikes);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncUserListsLikes...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    void syncUserFollowing(String slug, final SyncCallback callback) {
        service.syncUserFollowing(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserFollowing(slug)))
                .doOnNext(new Consumer<List<Follower>>() {
                    @Override
                    public void accept(List<Follower> followers) {
                        RealmManager.insertOrUpdateFollowings(followers);
                    }
                }).compose(RxUtil.<List<Follower>>observableTransformer())
                .subscribe(new Consumer<List<Follower>>() {
                    @Override
                    public void accept(List<Follower> followers) throws Exception {
                        callback.onSyncUserFollowingSuccess(followers);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncUserFollowing...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    void syncUserFollowers(String slug, final SyncCallback callback) {
        service.syncUserFollowers(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserFollowers(slug)))
                .doOnNext(new Consumer<List<Follower>>() {
                    @Override
                    public void accept(List<Follower> followers) {
                        RealmManager.insertOrUpdateFollowers(followers);
                    }
                }).compose(RxUtil.<List<Follower>>observableTransformer())
                .subscribe(new Consumer<List<Follower>>() {
                    @Override
                    public void accept(List<Follower> followers) throws Exception {
                        callback.onSyncUserFollowersSuccess(followers);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("Sync数据:syncUserFollowers...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 获取人物海报url
     */
    private String getImageUrl(TmdbPersonImage tmdbPeopleImage) {
        if (tmdbPeopleImage != null) {
            List<TmdbPersonImage.Profiles> profiles = tmdbPeopleImage.getProfiles();
            if (profiles != null && profiles.size() > 0) {
                TmdbPersonImage.Profiles profile = profiles.get(0);
                if (profile != null && profile.getFile_path() != null && profile.getFile_path().length() > 0) {
                    return profile.getFile_path();
                }
            }
        }
        return null;
    }

    /**
     * 获取电影海报url
     */
    private void getPosterImageUrl(ArtImage image, List<TmdbMovieImage.Posters> data) {
        if (ListUtil.hasData(data)) {
            for (TmdbMovieImage.Posters poster : data) {
                // TODO: 2017/4/12 默认优先选择英文海报，之后可以优化为根据影片语言优先选择对应海报
                if ("en".equals(poster.getIso_639_1())) {
                    image.setRemotePosterUrl(StringUtil.buildImageUrl(poster.getFile_path(), ImageCacheManager.TYPE_POSTER));
                }
            }
            image.setRemotePosterUrl(StringUtil.buildImageUrl(data.get(0).getFile_path(), ImageCacheManager.TYPE_POSTER));
        }
    }

    /**
     * 获取电影背景url
     */
    private void getBackdropImageUrl(ArtImage image, List<TmdbMovieImage.Backdrops> data) {
        if (ListUtil.hasData(data)) {
            for (TmdbMovieImage.Backdrops backdrop : data) {
                // TODO: 2017/4/12 默认优先选择英文海报，之后可以优化为根据影片语言优先选择对应海报
                if ("en".equals(backdrop.getIso_639_1())) {
                    image.setRemoteBackdropUrl(StringUtil.buildImageUrl(backdrop.getFile_path(), ImageCacheManager.TYPE_BACKDROP));
                }
            }
            image.setRemoteBackdropUrl(StringUtil.buildImageUrl(data.get(0).getFile_path(), ImageCacheManager.TYPE_BACKDROP));
        }
    }

    private BaseMovie mergeMovieAndImage1(ArtImage image, List<BaseMovie> movies, int type) {
        for (BaseMovie movie : movies) {
            if (movie.getMovie().getIds().getTmdb() == image.getTmdbID()) {
                getMergedMovie(movie.getMovie(), image, type);
                return movie;
            }
        }
        return null;
    }

    protected Movie mergeMovieAndImage2(ArtImage image, List<Movie> movies, int type) {
        for (Movie movie : movies) {
            if (movie.getIds().getTmdb() == image.getTmdbID()) {
                return getMergedMovie(movie, image, type);
            }
        }
        return null;
    }

    private Movie getMergedMovie(Movie movie, ArtImage image, int type) {
        if (ImageCacheManager.TYPE_AVATAR != type) {
            createBitmap(image, type);
        }
        movie.setImage(image);
        return movie;
    }

    /**
     * 获取图片bitmap
     */
    private void createBitmap(ArtImage image, int type) {
        if (ImageCacheManager.TYPE_POSTER == type) {
            if (image.getLocalPosterUri() != null) {
                PaletteManager.getInstance().createBitmap(image.getTmdbID(), image.getLocalPosterUri());
//                    bitmap = Glide.with(MoCloud.context).load(image.getLocalPosterUri()).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            }
        } else if (ImageCacheManager.TYPE_BACKDROP == type) {
            if (image.getLocalBackdropUri() != null) {
                PaletteManager.getInstance().createBitmap(image.getTmdbID(), image.getLocalBackdropUri());
//                    bitmap = Glide.with(MoCloud.context).load(image.getLocalBackdropUri()).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            }
        }
    }

    protected void downloadMovieImage(List<BaseMovie> baseMovies, int type) {
        for (BaseMovie baseMovie : baseMovies) {
            ArtImage image = baseMovie.getMovie().getImage();
            if (ImageCacheManager.TYPE_POSTER == type && !TextUtils.isEmpty(image.getRemotePosterUrl())) {
                downloadImage(image.getTmdbID(), image.getRemotePosterUrl(), type);
            } else if (ImageCacheManager.TYPE_BACKDROP == type && !TextUtils.isEmpty(image.getRemoteBackdropUrl())) {
                downloadImage(image.getTmdbID(), image.getRemoteBackdropUrl(), type);
            }
        }
    }

    private void downloadPersonImage(List<Staff> staffs, int imageType) {
        if (staffs != null) {
            for (Staff staff : staffs) {
                TmdbPersonImage image = staff.getTmdbPersonImage();
                if (image != null && ListUtil.hasData(image.getProfiles())) {
                    downloadImage(staff.getPerson().getIds().getTmdb(), StringUtil.buildPeopleHeadshotUrl(image.getProfiles().get(0).getFile_path()), imageType);
                }
            }
        }
    }

    private void downloadImage(int tmdbID, String imageUrl, final int imageType) {
        //imageUrl等于null表示图片已缓存在本地，不需要下载
        if (TextUtils.isEmpty(imageUrl)) {
            return;
        }

        if (!PermissionsUtil.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Logger.d("没有读写权限，不下载");
            return;
        }

        final String fileName = "TMDB-" + tmdbID + StringUtil.getFileSuffix(imageUrl);
        service.downloadImage(imageUrl)
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        ImageCacheManager.writeToDisk(responseBody, fileName, imageType);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        Logger.d("图片下载出错:" + fileName);
                        e.printStackTrace();
                    }
                });
    }
}
