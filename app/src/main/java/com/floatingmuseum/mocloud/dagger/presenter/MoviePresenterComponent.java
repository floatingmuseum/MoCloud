package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.dagger.FragmentScoped;
import com.floatingmuseum.mocloud.dagger.repo.RepoComponent;
import com.floatingmuseum.mocloud.mainmovie.anticipated.MovieAnticipatedFragment;
import com.floatingmuseum.mocloud.mainmovie.boxoffice.MovieBoxOfficeFragment;
import com.floatingmuseum.mocloud.mainmovie.collected.MovieCollectedFragment;
import com.floatingmuseum.mocloud.mainmovie.moviedetail.MovieDetailActivity;
import com.floatingmuseum.mocloud.mainmovie.played.MoviePlayedFragment;
import com.floatingmuseum.mocloud.mainmovie.popular.MoviePopularFragment;
import com.floatingmuseum.mocloud.mainmovie.trending.MovieTrendingFragment;
import com.floatingmuseum.mocloud.mainmovie.watched.MovieWatchedFragment;

import dagger.Component;

/**
 * Created by Floatingmuseum on 2016/6/21.
 */
@FragmentScoped
@Component(dependencies = RepoComponent.class,modules = MoviePresenterModule.class)
public interface MoviePresenterComponent {
    void inject(MovieTrendingFragment movieTrendingFragment);
    void inject(MoviePopularFragment moviePopularFragment);
    void inject(MoviePlayedFragment moviePlayedFragment);
    void inject(MovieWatchedFragment movieWatchedFragment);
    void inject(MovieCollectedFragment movieCollectedFragment);
    void inject(MovieAnticipatedFragment movieAnticipatedFragment);
    void inject(MovieBoxOfficeFragment movieBoxOfficeFragment);
    void inject(MovieDetailActivity movieDetailActivity);
}
