package com.floatingmuseum.mocloud.mainmovie.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMovieDetailPresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMoviePresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MovieDetailPresenterModule;
import com.floatingmuseum.mocloud.dagger.presenter.MoviePresenterModule;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;


/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class MovieDetailActivity extends AppCompatActivity{
    public static final String MOVIE_ID = "MOVIE_ID";
    @Inject
    MovieDetailPresenter presenter;

    @Inject
    MovieDetailPresenter secondPresenter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        String movieID = getIntent().getStringExtra(MOVIE_ID);

        DaggerMovieDetailPresenterComponent.builder()
                .movieDetailPresenterModule(new MovieDetailPresenterModule(this))
                .repoComponent(((MoCloud)getApplication()).getRepoComponent())
                .build().inject(this);
        presenter.getData(movieID);
    }

    public void success(String title){
        Logger.d("数据获取成功："+title);
    }
}
