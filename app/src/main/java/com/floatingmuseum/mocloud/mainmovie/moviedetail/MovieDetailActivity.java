package com.floatingmuseum.mocloud.mainmovie.moviedetail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseDetailActivity;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMovieDetailPresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MovieDetailPresenterModule;
import com.floatingmuseum.mocloud.data.entity.Actor;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieDetail;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.NumberFormatUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


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
    @Bind(R.id.ll_crew)
    LinearLayout ll_crew;
    @Bind(R.id.ll_comments)
    LinearLayout ll_comments;

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

    public void onSuccess(MovieDetail movieDetail) {
        ImageLoader.load(this,movieDetail.getMovie().getImages().getPoster().getMedium(),iv_poster);
        tv_movie_title.setText(movieDetail.getMovie().getTitle());
        tv_released.setText(movieDetail.getMovie().getReleased());
        tv_runtime.setText(movieDetail.getMovie().getRuntime()+" mins");
        tv_language.setText(movieDetail.getMovie().getLanguage());
        Double rating = movieDetail.getMovie().getRating();
        tv_rating.setText(NumberFormatUtil.doubleFormatToString(rating,false,2));
        tv_overview.setText(movieDetail.getMovie().getOverview());

        initCrew(movieDetail.getPeople());
        initComments(movieDetail.getComments());
    }

    private void initCrew(People people) {
        Staff director = people.getCrew().getDirecting().get(0);
        if(director!=null){
            LinearLayout staff = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.staff_item,ll_crew,false);
            RatioImageView iv_staff_headshot = (RatioImageView) staff.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) staff.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView)staff.findViewById(R.id.tv_crew_realname);
            staff.findViewById(R.id.tv_crew_character).setVisibility(View.GONE);
            ImageLoader.load(this,director.getPerson().getImages().getHeadshot().getMedium(),iv_staff_headshot);
            tv_crew_job.setText(director.getJob());
            tv_crew_realname.setText(director.getPerson().getName());
            ll_crew.addView(staff);
        }

        List<Actor> actors = people.getCast();
        int showSize = actors.size()>5?5:actors.size();
        for (int i = 0; i < showSize; i++) {
            Actor actor = actors.get(i);
            LinearLayout staff = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.staff_item,ll_crew,false);
            RatioImageView iv_staff_headshot = (RatioImageView) staff.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) staff.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView)staff.findViewById(R.id.tv_crew_realname);
            TextView tv_crew_character = (TextView)staff.findViewById(R.id.tv_crew_character);
            ImageLoader.load(this,actor.getPerson().getImages().getHeadshot().getMedium(),iv_staff_headshot);
            tv_crew_job.setText("Actor");
            tv_crew_realname.setText(actor.getPerson().getName());
            tv_crew_character.setText(actor.getCharacter());
            ll_crew.addView(staff);
        }
    }

    private void initComments(List<Comment> comments){

    }
}
