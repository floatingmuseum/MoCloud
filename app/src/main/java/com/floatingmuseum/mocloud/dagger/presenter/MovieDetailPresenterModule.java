package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.ui.mainmovie.detail.MovieDetailActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Floatingmuseum on 2016/8/3.
 */
@Module
public class MovieDetailPresenterModule {
    private MovieDetailActivity movieDetailActivity;

    public MovieDetailPresenterModule(MovieDetailActivity movieDetailActivity){
        this.movieDetailActivity = movieDetailActivity;
    }

    @Provides
    MovieDetailActivity provideMovieDetailActivity(){
        return movieDetailActivity;
    }
}
