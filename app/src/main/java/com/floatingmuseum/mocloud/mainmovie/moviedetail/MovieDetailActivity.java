package com.floatingmuseum.mocloud.mainmovie.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseDetailActivity;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMovieDetailPresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MovieDetailPresenterModule;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.NumberFormatUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class MovieDetailActivity extends AppCompatActivity implements BaseDetailActivity{
    public static final String MOVIE_ID = "MOVIE_ID";
    @Inject
    MovieDetailPresenter presenter;

    @Bind(R.id.poster)
    RatioImageView iv_poster;
    @Bind(R.id.tv_movie_title)
    TextView tv_movie_title;
    @Bind(R.id.tv_released)
    TextView tv_released;
    @Bind(R.id.tv_runtime)
    TextView tv_runtime;
    @Bind(R.id.tv_language)
    TextView tv_language;
    @Bind(R.id.tv_rating)
    TextView tv_rating;
    @Bind(R.id.tv_overview)
    TextView tv_overview;

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
        ImageLoader.load(this,movie.getImages().getPoster().getMedium(),iv_poster);
        tv_movie_title.setText(movie.getTitle());
        tv_released.setText(movie.getReleased());
        tv_runtime.setText(movie.getRuntime()+" mins");
        tv_language.setText(movie.getLanguage());
        Double rating = movie.getRating();
        Logger.d("Rating:"+rating+"..."+ NumberFormatUtil.doubleFormatToString(rating,false,2)+"..."+NumberFormatUtil.doubleFormat(rating,false,2));
        tv_rating.setText(NumberFormatUtil.doubleFormatToString(rating,false,2));
        tv_overview.setText(movie.getOverview());
    }
}
