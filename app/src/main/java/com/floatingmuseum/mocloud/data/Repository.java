package com.floatingmuseum.mocloud.data;

import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.MovieCommentsCallback;
import com.floatingmuseum.mocloud.data.callback.MovieDetailCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/7/11.
 */
@Singleton
public class Repository{

    protected MoCloudService service;
    public static final String COMMENTS_SORT_LIKES = "likes";
    public static final String COMMENTS_SORT_NEWEST = "newest";
    public static final String COMMENTS_SORT_OLDEST = "oldest";
    public static final String COMMENTS_SORT_REPLIES = "replies";
    @Inject
    public Repository(){
        service = MoCloudFactory.getInstance();
    }

    public void getMovieTrendingData(int pageNum,int limit,final DataCallback<List<BaseMovie>> callback){
        Logger.d("getMovieTrendingData");
        service.getMovieTrending(pageNum,limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<BaseMovie>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(e instanceof HttpException){
                            HttpException exception = (HttpException)e;
                            Logger.d("trending Data on Error:"+exception.response().errorBody().toString());
                        }
                        callback.onError(e);
                    }

                    @Override
                    public void onNext(List<BaseMovie> movies) {
                        callback.onBaseDataSuccess(movies);
                    }
                });
    }

    public void getMoviePopularData(int pageNum,int limit,final DataCallback callback){
        service.getMoviePopular(pageNum,limit)
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

    public void getMoviePlayedData(String period,int pageNum,int limit,final DataCallback callback){
        service.getMoviePlayed(period,pageNum,limit)
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

    public void getMovieWatchedData(String period,int pageNum,int limit,final DataCallback callback){
        service.getMovieWatched(period,pageNum,limit)
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

    public void getMovieCollectedData(String period, int pageNum,int limit, final DataCallback callback){
        service.getMovieCollected(period,pageNum,limit)
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

    public void getMovieAnticipatedData(int pageNum,int limit,final DataCallback callback){
        service.getMovieAnticipated(pageNum,limit)
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

    public void getMovieBoxOfficeData(final DataCallback callback){
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

    public void getMovieDetail(String movieId, final MovieDetailCallback callback){
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

    public void getMoviePeople(String movieId,final MovieDetailCallback callback){
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

    public void getMovieComments(String movieId, String commentsSort, int limit, int page, final MovieDetailCallback movieDetailCallback, final MovieCommentsCallback commentsCallback){
        service.getComments(movieId,commentsSort,limit,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Comment>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if(movieDetailCallback!=null){
                            movieDetailCallback.onError(e);
                        }else{
                            commentsCallback.onError(e);
                        }
                    }

                    @Override
                    public void onNext(List<Comment> comments) {
                        if(movieDetailCallback!=null){
                            movieDetailCallback.onCommentsSuccess(comments);
                        }else{
                            commentsCallback.onBaseDataSuccess(comments);
                        }
                    }
                });
    }
}
