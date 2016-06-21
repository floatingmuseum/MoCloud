package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.mainmovie.anticipated.MovieAnticipatedContract;
import com.floatingmuseum.mocloud.mainmovie.boxoffice.MovieBoxOfficeContract;
import com.floatingmuseum.mocloud.mainmovie.collected.MovieCollectedContract;
import com.floatingmuseum.mocloud.mainmovie.played.MoviePlayedContract;
import com.floatingmuseum.mocloud.mainmovie.popular.MoviePopularContract;
import com.floatingmuseum.mocloud.mainmovie.trending.MovieTrendingContract;
import com.floatingmuseum.mocloud.mainmovie.watched.MovieWatchedContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Floatingmuseum on 2016/6/21.
 */
@Module
public class MoviePresenterModule {
    private MovieTrendingContract.View trendingView;
    private MoviePopularContract.View popularView;
    private MoviePlayedContract.View playedView;
    private MovieWatchedContract.View watchedView;
    private MovieCollectedContract.View collectedView;
    private MovieAnticipatedContract.View anticipatedView;
    private MovieBoxOfficeContract.View boxOfficeView;

    public MoviePresenterModule(MovieTrendingContract.View trendingView){
        this.trendingView = trendingView;
    }

    public MoviePresenterModule(MoviePopularContract.View popularView){
        this.popularView = popularView;
    }

    public MoviePresenterModule(MoviePlayedContract.View playedView){
        this.playedView = playedView;
    }

    public MoviePresenterModule(MovieWatchedContract.View watchedView){
        this.watchedView = watchedView;
    }

    public MoviePresenterModule(MovieCollectedContract.View collectedView){
        this.collectedView = collectedView;
    }

    public MoviePresenterModule(MovieAnticipatedContract.View anticipatedView){
        this.anticipatedView = anticipatedView;
    }

    public MoviePresenterModule(MovieBoxOfficeContract.View boxOfficeView){
        this.boxOfficeView = boxOfficeView;
    }

    @Provides
    MovieTrendingContract.View provideTrendingMovieView(){
        return  trendingView;
    }

    @Provides
    MoviePopularContract.View providePopularMovieView(){
        return popularView;
    }

    @Provides
    MoviePlayedContract.View providePlayedMovieView(){
        return playedView;
    }

    @Provides
    MovieWatchedContract.View provideWatchedMovieView(){
        return watchedView;
    }

    @Provides
    MovieCollectedContract.View provideCollectedMovieView(){
        return collectedView;
    }

    @Provides
    MovieAnticipatedContract.View provideAnticipatedMovieView(){
        return anticipatedView;
    }

    @Provides
    MovieBoxOfficeContract.View provideBoxOfficeMovieView(){
        return boxOfficeView;
    }
}
