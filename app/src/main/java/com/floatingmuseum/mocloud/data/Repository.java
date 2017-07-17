package com.floatingmuseum.mocloud.data;

import android.Manifest;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.floatingmuseum.mocloud.BuildConfig;
import com.floatingmuseum.mocloud.Constants;
import com.floatingmuseum.mocloud.MoCloud;
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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


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

    /******************************************
     * 首页数据
     ********************************************************/


    /**
     * 趋势
     */
    public void getMovieTrendingData(int pageNum, int limit, final DataCallback<List<BaseMovie>> callback) {
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
                        callback.onBaseDataSuccess(movies);
                        downloadMovieImage(movies, ImageCacheManager.TYPE_POSTER);
                    }
                });
    }

    /**
     * 流行
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
                        return mergeMovieAndImage2(image, movies);
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
     * 最多play
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
     * 最多watch
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
     * 最多收藏
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
     * 即将上映
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

    /**
     * 获取列表中各个电影的海报
     */
    private Observable.Transformer<List<BaseMovie>, List<BaseMovie>> getEachPoster(final List<BaseMovie> movies, final int type) {
        return new Observable.Transformer<List<BaseMovie>, List<BaseMovie>>() {
            @Override
            public Observable<List<BaseMovie>> call(Observable<List<BaseMovie>> listObservable) {
                return listObservable.flatMap(new Func1<List<BaseMovie>, Observable<BaseMovie>>() {
                    @Override
                    public Observable<BaseMovie> call(List<BaseMovie> baseMovies) {
                        movies.addAll(baseMovies);
                        return Observable.from(movies);
                    }
                })
                        .flatMap(new Func1<BaseMovie, Observable<ArtImage>>() {
                            @Override
                            public Observable<ArtImage> call(BaseMovie baseMovie) {
                                return getArtImageObservable(baseMovie.getMovie().getIds().getTmdb(), type).subscribeOn(Schedulers.io());
                            }
                        })
                        .map(new Func1<ArtImage, BaseMovie>() {
                            @Override
                            public BaseMovie call(ArtImage image) {
                                return mergeMovieAndImage1(image, movies);
                            }
                        })
                        .toList();
            }
        };
    }

    private Observable<ArtImage> getArtImageObservable(final int tmdbID, final int type) {
        File file = ImageCacheManager.hasCacheImage(tmdbID, type);
        if (file != null) {
            return ImageCacheManager.localArtImage(tmdbID, file,type);
        } else {
            return service.getTmdbImages(tmdbID, BuildConfig.TmdbApiKey)
                    .onErrorReturn(new Func1<Throwable, TmdbMovieImage>() {
                        @Override
                        public TmdbMovieImage call(Throwable throwable) {
                            Logger.d("getTmdbImages...onErrorReturn");
                            throwable.printStackTrace();
                            TmdbMovieImage tmdbMovieImage = new TmdbMovieImage();
                            tmdbMovieImage.setId(tmdbID);
                            return tmdbMovieImage;
                        }
                    }).map(new Func1<TmdbMovieImage, ArtImage>() {
                        @Override
                        public ArtImage call(TmdbMovieImage tmdbMovieImage) {
                            ArtImage image = new ArtImage();
                            image.setTmdbID(tmdbMovieImage.getId());
                            getPosterImageUrl(image, tmdbMovieImage.getPosters());
                            getBackdropImageUrl(image, tmdbMovieImage.getBackdrops());
                            return image;
                        }
                    });
//            }
        }
    }

    /**
     * 从本地or网络获取电影海报
     */
    private Observable<ArtImage> getTmdbMovieImageObservable(final Movie movie) {
        Logger.d("getTmdbMovieImageObservable...Movie:" + movie.getTitle() + "...TmdbId:" + movie.getIds().getTmdb());
        int tmdbId = movie.getIds().getTmdb();
        File file = ImageCacheManager.hasCacheImage(tmdbId, ImageCacheManager.TYPE_POSTER);
        if (file != null) {
            return ImageCacheManager.localArtImage(tmdbId, file,ImageCacheManager.TYPE_POSTER);
        }
        return service.getTmdbImages(tmdbId, BuildConfig.TmdbApiKey)
                .onErrorReturn(new Func1<Throwable, TmdbMovieImage>() {
                    @Override
                    public TmdbMovieImage call(Throwable throwable) {
                        Logger.d("getTmdbImages...onErrorReturn");
                        throwable.printStackTrace();
                        TmdbMovieImage tmdbMovieImage = new TmdbMovieImage();
                        tmdbMovieImage.setId(movie.getIds().getTmdb());
                        return tmdbMovieImage;
                    }
                }).map(new Func1<TmdbMovieImage, ArtImage>() {
                    @Override
                    public ArtImage call(TmdbMovieImage tmdbMovieImage) {
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

    public Subscription getMovieDetail(String movieId, final MovieDetailCallback<Movie> callback) {
        return service.getMovieDetail(movieId)
                .compose(RxUtil.<Movie>threadSwitch())
                .subscribe(new SimpleObserver<Movie>() {
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
        final MovieTeam team = new MovieTeam();
        return service.getMovieTeam(slug)
                .map(new Func1<PeopleCredit, MovieTeam>() {
                    @Override
                    public MovieTeam call(PeopleCredit credit) {
                        //这里去DataMachine里处理一下数据，旨在提取出1个导演3个主演用于MovieDetail显示，剩下一个原始List用于MovieDetail中点击more时使用
                        MovieTeam tempTeam = DataMachine.mixingStaffsWorks(credit);
                        team.setDetailShowList(tempTeam.getDetailShowList());
                        team.setPeopleCredit(credit);
                        return team;
                    }
                })
                .flatMap(new Func1<MovieTeam, Observable<Staff>>() {
                    @Override
                    public Observable<Staff> call(MovieTeam movieTeam) {
                        return Observable.from(movieTeam.getDetailShowList());
                    }
                })
                .flatMap(new Func1<Staff, Observable<TmdbPersonImage>>() {
                    @Override
                    public Observable<TmdbPersonImage> call(Staff staff) {
                        return getTmdbStaffImageObservable(staff);
                    }
                })
                .map(new Func1<TmdbPersonImage, Staff>() {
                    @Override
                    public Staff call(TmdbPersonImage tmdbPersonImage) {
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
                .map(new Func1<List<Staff>, List<Staff>>() {
                    @Override
                    public List<Staff> call(List<Staff> staffs) {
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
                .compose(RxUtil.<List<Staff>>threadSwitch())
                .subscribe(new SimpleObserver<List<Staff>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getMovieTeam...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<Staff> staffs) {
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
                });
    }

    /**
     * 电影其他评分
     */
    public Subscription getMovieOtherRatings(String imdbID, final MovieDetailCallback callback) {
        return service.getMovieOtherRatings(imdbID, "true")
                .compose(RxUtil.<OmdbInfo>threadSwitch())
                .subscribe(new SimpleObserver<OmdbInfo>() {
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
    public Subscription getMovieComments(String movieId, String sortCondition, int limit, int page, final MovieDetailCallback movieDetailCallback, final CommentsCallback<List<Comment>> commentsCallback) {
        Logger.d("加载Comment:" + movieId + "..." + sortCondition + "..." + limit + "..." + page + "..." + movieDetailCallback + "..." + commentsCallback);
        return service.getComments(movieId, sortCondition, limit, page)
                .doOnNext(RxUtil.updateCommentsResultLikesState())
                .compose(RxUtil.<List<Comment>>threadSwitch())
                .subscribe(new SimpleObserver<List<Comment>>() {
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
    public Subscription getCommentReplies(long commentId, final DataCallback<List<Comment>> callback) {
        return service.getCommentReplies(commentId)
                .doOnNext(RxUtil.updateCommentsResultLikesState())
                .compose(RxUtil.<List<Comment>>threadSwitch())
                .subscribe(new SimpleObserver<List<Comment>>() {
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
    public Subscription sendReply(long id, Reply reply, final CommentsCallback callback) {
        Logger.d("sendReply:" + id + "..." + reply.getComment());
        return service.sendReply(id, reply)
                .onErrorResumeNext(refreshTokenAndRetry(service.sendReply(id, reply)))
                .compose(RxUtil.<Comment>threadSwitch())
                .subscribe(new SimpleObserver<Comment>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("sendReply...onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Comment comment) {
                        callback.onSendCommentSuccess(comment);
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
        })
                .onErrorResumeNext(refreshTokenAndRetry(service.sendComment(comment)))
                .compose(RxUtil.<Comment>threadSwitch())
                .subscribe(new SimpleObserver<Comment>() {
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

    public void addCommentToLikes(final Comment comment, final CommentsCallback callback) {
        service.addCommentToLikes(comment.getId())
                .onErrorResumeNext(refreshTokenAndRetry(service.addCommentToLikes(comment.getId())))
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
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
                .compose(RxUtil.<ResponseBody>threadSwitch())
                .subscribe(new SimpleObserver<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("评论点赞测试:addCommentToLikes...点赞失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Logger.d("评论点赞测试:addCommentToLikes...点赞成功");
                        callback.onAddCommentToLikesSuccess(comment.getId());
                    }
                });
    }

    public void removeCommentFromLikes(final Comment comment, final CommentsCallback callback) {
        service.removeCommentFromLikes(comment.getId())
                .onErrorResumeNext(refreshTokenAndRetry(service.removeCommentFromLikes(comment.getId())))
                .doOnNext(new Action1<ResponseBody>() {
                    @Override
                    public void call(ResponseBody responseBody) {
                        RealmManager.delete(RealmCommentLike.class, "id", comment.getId());
                    }
                })
                .compose(RxUtil.<ResponseBody>threadSwitch())
                .subscribe(new SimpleObserver<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("评论点赞测试:removeCommentFromLikes...取消点赞失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Logger.d("评论点赞测试:removeCommentFromLikes...取消点赞成功");
                        callback.onRemoveCommentFromLikesSuccess(comment.getId());
                    }
                });
    }

    /******************************************
     * 影人数据
     ********************************************************/

    /**
     * 获取人物电影作品
     */
    public Subscription getStaffMovieCredits(int traktId, final DataCallback<List<Staff>> callback) {
        return service.getStaffMovieCredits(traktId)
                .map(new Func1<PeopleCredit, List<Staff>>() {
                    @Override
                    public List<Staff> call(PeopleCredit credits) {
                        return DataMachine.mixingPersonWorks(credits);
                    }
                })
                .compose(RxUtil.<List<Staff>>threadSwitch())
                .subscribe(new SimpleObserver<List<Staff>>() {
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

    public Subscription getWorksImages(final List<Staff> subWorks, final StaffWorksCallback callback) {
        return Observable.from(subWorks)
                .flatMap(new Func1<Staff, Observable<ArtImage>>() {
                    @Override
                    public Observable<ArtImage> call(Staff staff) {
                        Logger.d("getWorksImages...getImage");
//                        return getTmdbMovieImageObservable(staff.getMovie());
                        return getArtImageObservable(staff.getMovie().getIds().getTmdb(), ImageCacheManager.TYPE_POSTER);
                    }
                }).map(new Func1<ArtImage, Staff>() {
                    @Override
                    public Staff call(ArtImage image) {
                        Logger.d("getWorksImages...merge..." + image);
                        for (Staff staff : subWorks) {
                            Movie movie = staff.getMovie();
                            if (movie.getIds().getTmdb() == image.getTmdbID()) {
                                Logger.d("getWorksImages...movieId:" + movie.getIds().getTmdb() + "...imageId:" + image.getTmdbID());
                                staff.setMovie(getMergedMovie(movie, image));
                                return staff;
                            }
                        }
                        return null;
                    }
                }).toList()
                .compose(RxUtil.<List<Staff>>threadSwitch())
                .subscribe(new SimpleObserver<List<Staff>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("getWorksImages...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<Staff> staffs) {
                        callback.onGetWorksImagesSuccess(staffs);
                    }
                });
    }

    /**
     * 获取人物其他图片
     */
    public Subscription getStaffImages(int tmdbId, final DataCallback<StaffImages> callback) {
        return service.getStaffImages(tmdbId, BuildConfig.TmdbApiKey)
                .compose(RxUtil.<StaffImages>threadSwitch())
                .subscribe(new SimpleObserver<StaffImages>() {
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
    public void getAccessToken(String code, final DataCallback<TraktToken> dataCallback) {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setCode(code);
        tokenRequest.setClient_id(BuildConfig.TraktID);
        tokenRequest.setClient_secret(BuildConfig.TraktSecret);
        tokenRequest.setRedirect_uri(Constants.REDIRECT_URI);
        tokenRequest.setGrant_type(Constants.GRANT_TYPE_AUTHORIZATION_CODE);

        service.getToken(tokenRequest)
                .compose(RxUtil.<TraktToken>threadSwitch())
                .subscribe(new SimpleObserver<TraktToken>() {
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
     * **********************************************************
     * <p>
     * /**
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
                .subscribe(new SimpleObserver<Stats>() {
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
     * 获取当前用户推荐
     */
    public Subscription getRecommendations(final RecommendationsCallback<List<Movie>> callback) {
        final List<Movie> movies = new ArrayList<>();
        return service.getRecommendations()
                .onErrorResumeNext(refreshTokenAndRetry(service.getRecommendations()))
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
                        return getArtImageObservable(movie.getIds().getTmdb(), ImageCacheManager.TYPE_POSTER);
                    }
                })
                .map(new Func1<ArtImage, Movie>() {
                    @Override
                    public Movie call(ArtImage image) {
                        return mergeMovieAndImage2(image, movies);
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
                        for (Movie movie : movies) {
                            downloadImage(movie.getIds().getTmdb(), movie.getImage().getRemotePosterUrl(), ImageCacheManager.TYPE_POSTER);
                        }
                    }
                });
    }

    public Subscription getFeatureList(final String userID, final String listID, final RecommendationsCallback callback) {
        return service.getFeatureList(userID, listID)
                .onErrorResumeNext(refreshTokenAndRetry(service.getFeatureList(userID, listID)))
                .compose(RxUtil.<FeatureList>threadSwitch())
                .subscribe(new SimpleObserver<FeatureList>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("FeatureList...onError:" + userID + "..." + listID);
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(FeatureList featureList) {
                        Logger.d("FeatureList...onNext:" + userID + "..." + listID);
                        callback.onGetFeatureListSuccess(featureList);
                    }
                });
    }

    public Subscription getFeatureListData(final String userID, final String listID, final RecommendationsCallback callback) {
        return service.getFeatureListData(userID, listID)
                .onErrorResumeNext(refreshTokenAndRetry(service.getFeatureListData(userID, listID)))
                .compose(RxUtil.<List<FeatureListItem>>threadSwitch())
                .subscribe(new SimpleObserver<List<FeatureListItem>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("FeatureList...onError:" + userID + "..." + listID);
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<FeatureListItem> data) {
                        Logger.d("FeatureList...onNext:" + userID + "..." + listID);
                        callback.onGetFeatureListDataSuccess(listID, data);
                    }
                });
    }

    /**
     * 从电影推荐中隐藏不感兴趣的电影
     */
    public Subscription hideMovie(String slug, final RecommendationsCallback callback) {
        return service.hideMovie(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.hideMovie(slug)))
                .compose(RxUtil.<ResponseBody>threadSwitch())
                .subscribe(new SimpleObserver<ResponseBody>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        callback.onHideMovieSuccess();
                    }
                });
    }

    /**
     * 获取用户最后动态时间
     */
    void getLastActivities(final SyncCallback callback) {
        service.getLastActivities()
                .onErrorResumeNext(refreshTokenAndRetry(service.getLastActivities()))
                .compose(RxUtil.<LastActivities>threadSwitch())
                .subscribe(new SimpleObserver<LastActivities>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:getLastActivities...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(LastActivities lastActivities) {
                        callback.onLastActivitiesSuccess(lastActivities);
                    }
                });
    }

    /**
     * 获取UserSettings
     */
    public Subscription getUserSettings(final DataCallback<UserSettings> callback) {
        return service.syncUserSettings()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserSettings()))
                .compose(RxUtil.<UserSettings>threadSwitch())
                .subscribe(new SimpleObserver<UserSettings>() {
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
     * 获取UserSettings
     */
    Subscription syncUserSettings(final SyncCallback callback) {
        return service.syncUserSettings()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserSettings()))
                .compose(RxUtil.<UserSettings>threadSwitch())
                .subscribe(new SimpleObserver<UserSettings>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncUserSettings...onError");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(UserSettings userSettings) {
                        Logger.d("UserSettings:onNext:" + userSettings);
                        callback.onSyncUserSettingsSuccess(userSettings);
                    }
                });
    }

    void syncMovieWatched(final SyncCallback callback) {
        service.syncMovieWatched()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMovieWatched()))
                .doOnNext(new Action1<List<MovieWatchedItem>>() {
                    @Override
                    public void call(List<MovieWatchedItem> movieWatchedItems) {
                        Logger.d("Sync数据:看过:" + movieWatchedItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieWatched(movieWatchedItems);
                    }
                })
                .compose(RxUtil.<List<MovieWatchedItem>>threadSwitch())
                .subscribe(new SimpleObserver<List<MovieWatchedItem>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncMovieWatched...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<MovieWatchedItem> movieWatchedItems) {
                        callback.onSyncMovieWatchedSuccess(movieWatchedItems);
                    }
                });
    }

    public void addMovieToWatched(final SyncData item, final MovieDetailCallback callback) {
        service.addMovieToWatched(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.addMovieToWatched(item)))
                .doOnNext(new Action1<SyncResponse>() {
                    @Override
                    public void call(SyncResponse syncResponse) {
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
                .compose(RxUtil.<SyncResponse>threadSwitch())
                .subscribe(new SimpleObserver<SyncResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("看过测试:addMovieToWatched:...add失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(SyncResponse syncResponse) {
                        Logger.d("看过测试:addMovieToWatched:...add成功");
                        callback.onAddMovieToWatchedSuccess(syncResponse);
                    }
                });
    }

    public void removeMovieFromWatched(final SyncData item, final MovieDetailCallback callback) {
        service.removeMovieFromWatched(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.removeMovieFromWatched(item)))
                .doOnNext(new Action1<SyncResponse>() {
                    @Override
                    public void call(SyncResponse syncResponse) {
                        RealmManager.delete(RealmMovieWatched.class, "trakt_id", item.getMovies().get(0).getIds().getTrakt());
                    }
                })
                .compose(RxUtil.<SyncResponse>threadSwitch())
                .subscribe(new SimpleObserver<SyncResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("看过测试:addMovieToWatched:...remove失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(SyncResponse syncResponse) {
                        Logger.d("看过测试:addMovieToWatched:...remove成功..." + syncResponse.getDeleted().getMovies());
                        callback.onRemoveMovieFromWatchedSuccess(syncResponse);
                    }
                });
    }

    void syncMovieWatchlist(final SyncCallback callback) {
        service.syncMovieWatchlist()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMovieWatchlist()))
                .doOnNext(new Action1<List<MovieWatchlistItem>>() {
                    @Override
                    public void call(List<MovieWatchlistItem> movieWatchlistItems) {
                        Logger.d("Sync数据:想看:" + movieWatchlistItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieWatchlist(movieWatchlistItems);
                    }
                })
                .compose(RxUtil.<List<MovieWatchlistItem>>threadSwitch())
                .subscribe(new SimpleObserver<List<MovieWatchlistItem>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncMovieWatchlist...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<MovieWatchlistItem> movieWatchlistItems) {
                        callback.onSyncMovieWatchlistSuccess(movieWatchlistItems);
                    }
                });
    }

    public void addMovieToWatchlist(final SyncData item, final MovieDetailCallback callback) {
        service.addMovieToWatchlist(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.addMovieToWatchlist(item)))
                .doOnNext(new Action1<SyncResponse>() {
                    @Override
                    public void call(SyncResponse syncResponse) {
                        RealmMovieWatchlist realmMovieWatchlist = new RealmMovieWatchlist();
                        realmMovieWatchlist.setTitle(item.getMovies().get(0).getTitle());
                        realmMovieWatchlist.setYear(item.getMovies().get(0).getYear());
                        realmMovieWatchlist.setTrakt_id(item.getMovies().get(0).getIds().getTrakt());
                        realmMovieWatchlist.setListed_at(item.getMovies().get(0).getListed_at());
                        RealmManager.insertOrUpdate(realmMovieWatchlist);
                    }
                })
                .compose(RxUtil.<SyncResponse>threadSwitch())
                .subscribe(new SimpleObserver<SyncResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("想看测试:addMovieToWatchlist:...add失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(SyncResponse syncResponse) {
                        Logger.d("想看测试:addMovieToWatchlist:...add成功..." + syncResponse.getAdded().getMovies());
                        callback.onAddMovieToWatchlistSuccess(syncResponse);
                    }
                });
    }

    public void removeMovieFromWatchlist(final SyncData item, final MovieDetailCallback callback) {
        service.removeMovieFromWatchlist(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.removeMovieFromWatchlist(item)))
                .doOnNext(new Action1<SyncResponse>() {
                    @Override
                    public void call(SyncResponse syncResponse) {
                        RealmManager.delete(RealmMovieWatchlist.class, "trakt_id", item.getMovies().get(0).getIds().getTrakt());
                    }
                })
                .compose(RxUtil.<SyncResponse>threadSwitch())
                .subscribe(new SimpleObserver<SyncResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("想看测试:removeMovieFromWatchlist:...remove失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(SyncResponse syncResponse) {
                        Logger.d("想看测试:removeMovieFromWatchlist:...remove成功..." + syncResponse.getDeleted().getMovies());
                        callback.onRemoveMovieFromWatchlistSuccess(syncResponse);
                    }
                });
    }

    void syncMovieRatings(final SyncCallback callback) {
        service.syncMovieRatings()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMovieRatings()))
                .doOnNext(new Action1<List<MovieRatingItem>>() {
                    @Override
                    public void call(List<MovieRatingItem> movieRatingItems) {
                        Logger.d("Sync数据:评分:" + movieRatingItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieRating(movieRatingItems);
                    }
                })
                .compose(RxUtil.<List<MovieRatingItem>>threadSwitch())
                .subscribe(new SimpleObserver<List<MovieRatingItem>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncMovieRatings...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<MovieRatingItem> movieRatingItems) {
                        callback.onSyncMovieRatingsSuccess(movieRatingItems);
                    }
                });
    }

    void syncMovieCollection(final SyncCallback callback) {
        service.syncMoviesCollections()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncMoviesCollections()))
                .doOnNext(new Action1<List<MovieCollectionItem>>() {
                    @Override
                    public void call(List<MovieCollectionItem> movieCollectionItems) {
                        Logger.d("Sync数据:收藏:" + movieCollectionItems.size() + "电影");
                        RealmManager.insertOrUpdateMovieCollection(movieCollectionItems);
                    }
                })
                .compose(RxUtil.<List<MovieCollectionItem>>threadSwitch())
                .subscribe(new SimpleObserver<List<MovieCollectionItem>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncMovieCollection...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<MovieCollectionItem> movieCollectionItems) {
                        callback.onSyncMovieCollectionSuccess(movieCollectionItems);
                    }
                });
    }

    public void addMovieToCollection(final SyncData item, final MovieDetailCallback callback) {
        // TODO: 2017/3/3 add various metadata
        service.addMovieToCollection(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.addMovieToCollection(item)))
                .doOnNext(new Action1<SyncResponse>() {
                    @Override
                    public void call(SyncResponse syncResponse) {
                        RealmMovieCollection realmMovieCollection = new RealmMovieCollection();
                        realmMovieCollection.setTitle(item.getMovies().get(0).getTitle());
                        realmMovieCollection.setYear(item.getMovies().get(0).getYear());
                        realmMovieCollection.setTrakt_id(item.getMovies().get(0).getIds().getTrakt());
                        realmMovieCollection.setCollected_at(item.getMovies().get(0).getCollected_at());
                        RealmManager.insertOrUpdate(realmMovieCollection);
                    }
                })
                .compose(RxUtil.<SyncResponse>threadSwitch())
                .subscribe(new SimpleObserver<SyncResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("收藏测试:addMovieToCollection:...add");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(SyncResponse syncResponse) {
                        Logger.d("收藏测试:addMovieToCollection:...add成功..." + syncResponse.getAdded().getMovies());
                        callback.onAddMovieToCollectionSuccess(syncResponse);
                    }
                });
    }

    public void removeMovieFromCollection(final SyncData item, final MovieDetailCallback callback) {
        service.removeMovieFromCollection(item)
                .onErrorResumeNext(refreshTokenAndRetry(service.removeMovieFromCollection(item)))
                .doOnNext(new Action1<SyncResponse>() {
                    @Override
                    public void call(SyncResponse syncResponse) {
                        RealmManager.delete(RealmMovieCollection.class, "trakt_id", item.getMovies().get(0).getIds().getTrakt());
                    }
                })
                .compose(RxUtil.<SyncResponse>threadSwitch())
                .subscribe(new SimpleObserver<SyncResponse>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("收藏测试:removeMovieFromCollection:...remove失败");
                        e.printStackTrace();
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(SyncResponse syncResponse) {
                        Logger.d("收藏测试:removeMovieFromCollection:...remove成功..." + syncResponse.getDeleted().getMovies());
                        callback.onRemoveMovieFromCollectionSuccess(syncResponse);
                    }
                });
    }

    void syncUserCommentsLikes(final SyncCallback callback) {
        service.syncUserCommentsLikes()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserCommentsLikes()))
                .doOnNext(new Action1<List<UserCommentLike>>() {
                    @Override
                    public void call(List<UserCommentLike> userCommentLikes) {
                        Logger.d("Sync数据:评论点赞:" + userCommentLikes.size());
                        RealmManager.insertOrUpdateUserCommentsLikes(userCommentLikes);
                    }
                })
                .compose(RxUtil.<List<UserCommentLike>>threadSwitch())
                .subscribe(new SimpleObserver<List<UserCommentLike>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncUserCommentsLikes...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<UserCommentLike> userCommentLikes) {
                        callback.onSyncUserCommentsLikesSuccess(userCommentLikes);
                    }
                });
    }

    void syncUserListsLikes(final SyncCallback callback) {
        service.syncUserListsLikes()
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserListsLikes()))
                .doOnNext(new Action1<List<UserListLike>>() {
                    @Override
                    public void call(List<UserListLike> userListLikes) {
                        Logger.d("Sync数据:列表点赞:" + userListLikes.size());
                        RealmManager.insertOrUpdateUserListsLikes(userListLikes);
                    }
                })
                .compose(RxUtil.<List<UserListLike>>threadSwitch())
                .subscribe(new SimpleObserver<List<UserListLike>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncUserListsLikes...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<UserListLike> userListLikes) {
                        callback.onSyncUserListLikesSuccess(userListLikes);
                    }
                });
    }

    void syncUserFollowing(String slug, final SyncCallback callback) {
        service.syncUserFollowing(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserFollowing(slug)))
                .doOnNext(new Action1<List<Follower>>() {
                    @Override
                    public void call(List<Follower> followers) {
                        RealmManager.insertOrUpdateFollowings(followers);
                    }
                }).compose(RxUtil.<List<Follower>>threadSwitch())
                .subscribe(new SimpleObserver<List<Follower>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncUserFollowing...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Follower> followers) {
                        callback.onSyncUserFollowingSuccess(followers);
                    }
                });
    }

    void syncUserFollowers(String slug, final SyncCallback callback) {
        service.syncUserFollowers(slug)
                .onErrorResumeNext(refreshTokenAndRetry(service.syncUserFollowers(slug)))
                .doOnNext(new Action1<List<Follower>>() {
                    @Override
                    public void call(List<Follower> followers) {
                        RealmManager.insertOrUpdateFollowers(followers);
                    }
                }).compose(RxUtil.<List<Follower>>threadSwitch())
                .subscribe(new SimpleObserver<List<Follower>>() {
                    @Override
                    public void onError(Throwable e) {
                        Logger.d("Sync数据:syncUserFollowers...onError");
                        callback.onError(e);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Follower> followers) {
                        callback.onSyncUserFollowersSuccess(followers);
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
                    image.setRemotePosterUrl(StringUtil.buildImageUrl(backdrop.getFile_path(), ImageCacheManager.TYPE_BACKDROP));
                }
            }
            image.setRemotePosterUrl(StringUtil.buildImageUrl(data.get(0).getFile_path(), ImageCacheManager.TYPE_BACKDROP));
        }
    }

    private BaseMovie mergeMovieAndImage1(ArtImage image, List<BaseMovie> movies) {
        for (BaseMovie movie : movies) {
            if (movie.getMovie().getIds().getTmdb() == image.getTmdbID()) {
                getMergedMovie(movie.getMovie(), image);
                return movie;
            }
        }
        return null;
    }

    private Movie mergeMovieAndImage2(ArtImage image, List<Movie> movies) {
        for (Movie movie : movies) {
            if (movie.getIds().getTmdb() == image.getTmdbID()) {
                return getMergedMovie(movie, image);
            }
        }
        return null;
    }

    private Movie getMergedMovie(Movie movie, ArtImage image) {
        image.setBitmap(getBitmap(image));
        movie.setImage(image);
        return movie;
    }

    /**
     * 获取图片bitmap
     */
    private Bitmap getBitmap(ArtImage image) {
        Bitmap bitmap = null;
        try {
            if (image.getLocalPosterUri() != null) {
                bitmap = Glide.with(MoCloud.context).load(image.getLocalPosterUri()).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } else {
                bitmap = Glide.with(MoCloud.context).load(image.getRemotePosterUrl()).asBitmap().into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    private void downloadMovieImage(List<BaseMovie> baseMovies, int type) {
        for (BaseMovie baseMovie : baseMovies) {
            ArtImage image = baseMovie.getMovie().getImage();
            if (!TextUtils.isEmpty(image.getRemotePosterUrl())) {
                downloadImage(image.getTmdbID(), image.getRemotePosterUrl(), type);
            }
            if (!TextUtils.isEmpty(image.getRemoteBackdropUrl()) && ImageCacheManager.TYPE_BACKDROP == type) {
                downloadImage(image.getTmdbID(), image.getRemoteBackdropUrl(), type);
            }
        }
    }

    private void downloadPersonImage(List<Staff> staffs, int imageType) {
        if (staffs != null) {
            for (Staff staff : staffs) {
//                ArtImage image = staff.getTmdbPersonImage();
//                downloadImage(image, imageType);
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
                .subscribe(new SimpleObserver<ResponseBody>() {
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
