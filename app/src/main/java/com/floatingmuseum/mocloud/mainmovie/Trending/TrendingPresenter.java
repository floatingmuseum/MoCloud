package com.floatingmuseum.mocloud.mainmovie.Trending;


import com.floatingmuseum.mocloud.model.entity.Image;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.entity.Trending;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class TrendingPresenter implements TrendingContract.Presenter {

    private TrendingContract.View mTrendingView;
    private TrendingRepo repo;
    public TrendingPresenter(TrendingContract.View trendingView){
        mTrendingView = trendingView;
        mTrendingView.setPresenter(this);
        repo = new TrendingRepo();
    }

    @Override
    public void start() {
        repo.getData()
                .subscribe(new Subscriber<List<Trending>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<Trending> trendings) {
                        getMovieDetail(trendings);
                    }
                });
    }

    public void getMovieDetail(final List<Trending> trendings){
        final List<Image> imageList = new ArrayList<>();
        Observable.from(trendings).flatMap(new Func1<Trending, Observable<Movie>>() {
            @Override
            public Observable<Movie> call(Trending trending) {
                return repo.getMovie(trending.getMovie().getIds().getSlug());
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe(new Subscriber<Movie>() {
            @Override
            public void onCompleted() {
                mTrendingView.refreshData(trendings,imageList);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Movie movie) {
                imageList.add(movie.getImages());
            }
        });
    }

    @Override
    public void onDestroy() {

    }
}
