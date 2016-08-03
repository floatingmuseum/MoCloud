package com.floatingmuseum.mocloud.mainmovie.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMovieDetailPresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MovieDetailPresenterModule;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class MovieDetailActivity extends AppCompatActivity {
    public static final String MOVIE_ID = "MOVIE_ID";
    @Inject
    MovieDetailPresenter presenter;

    @Bind(R.id.poster)
    RatioImageView poster;
    @Bind(R.id.tv_director)
    TextView director;
    @Bind(R.id.tv_released)
    TextView released;
    @Bind(R.id.tv_runtime)
    TextView runtime;
    @Bind(R.id.tv_language)
    TextView language;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviedetail);
        ButterKnife.bind(this);
        String movieID = getIntent().getStringExtra(MOVIE_ID);

        DaggerMovieDetailPresenterComponent.builder()
                .movieDetailPresenterModule(new MovieDetailPresenterModule(this))
                .repoComponent(((MoCloud) getApplication()).getRepoComponent())
                .build().inject(this);

        presenter.getData(movieID);
    }

    public void success(Movie movie) {
        Logger.d("before");
        ImageLoader.load(this,movie.getImages().getPoster().getMedium(),poster);

        released.setText(movie.getReleased());
        runtime.setText(movie.getRuntime());
        language.setText(movie.getLanguage());
        Logger.d("before...pic");
        Logger.d("after");
    }
}
