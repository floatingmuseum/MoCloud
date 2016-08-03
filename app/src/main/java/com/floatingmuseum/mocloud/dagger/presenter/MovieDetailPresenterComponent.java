package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.dagger.ActivityScope;
import com.floatingmuseum.mocloud.dagger.repo.RepoComponent;
import com.floatingmuseum.mocloud.mainmovie.moviedetail.MovieDetailActivity;

import dagger.Component;

/**
 * Created by Floatingmuseum on 2016/8/3.
 */
@ActivityScope
@Component(dependencies = RepoComponent.class,modules = MovieDetailPresenterModule.class)
public interface MovieDetailPresenterComponent {
    void inject(MovieDetailActivity movieDetailActivity);
}
