package com.floatingmuseum.mocloud.mainmovie.moviedetail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawableUtils;
import android.text.TextUtils;
import android.text.format.DateUtils;
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
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieDetail;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.NumberFormatUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class MovieDetailActivity extends AppCompatActivity implements BaseDetailActivity {
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
    @Bind(R.id.tv_no_comments)
    TextView tv_no_comments;

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

    public void onSuccess(Movie movie) {
        ImageLoader.load(this, movie.getImages().getPoster().getMedium(), iv_poster);
        tv_movie_title.setText(movie.getTitle());
        tv_released.setText(movie.getReleased());
        tv_runtime.setText(movie.getRuntime() + " mins");
        tv_language.setText(movie.getLanguage());
        Double rating = movie.getRating();
        tv_rating.setText(NumberFormatUtil.doubleFormatToString(rating, false, 2));
        tv_overview.setText(movie.getOverview());
    }

    public void onPeopleSuccess(People people) {
        Staff director = people.getCrew().getDirecting().get(0);
        if (director != null) {
            LinearLayout staff = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.staff_item, ll_crew, false);
            RatioImageView iv_staff_headshot = (RatioImageView) staff.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) staff.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) staff.findViewById(R.id.tv_crew_realname);
            staff.findViewById(R.id.tv_crew_character).setVisibility(View.GONE);
            ImageLoader.load(this, director.getPerson().getImages().getHeadshot().getMedium(), iv_staff_headshot);
            tv_crew_job.setText(director.getJob());
            tv_crew_realname.setText(director.getPerson().getName());
            ll_crew.addView(staff);
        }

        List<Actor> actors = people.getCast();
        int showSize = actors.size() > 5 ? 5 : actors.size();
        for (int i = 0; i < showSize; i++) {
            Actor actor = actors.get(i);
            LinearLayout staff_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.staff_item, ll_crew, false);
            RatioImageView iv_staff_headshot = (RatioImageView) staff_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) staff_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) staff_item.findViewById(R.id.tv_crew_realname);
            TextView tv_crew_character = (TextView) staff_item.findViewById(R.id.tv_crew_character);
            ImageLoader.load(this, actor.getPerson().getImages().getHeadshot().getMedium(), iv_staff_headshot);
            tv_crew_job.setText("Actor");
            tv_crew_realname.setText(actor.getPerson().getName());
            tv_crew_character.setText(actor.getCharacter());
            ll_crew.addView(staff_item);
        }
    }

    public void onCommentsSuccess(List<Comment> comments) {
        if (comments.size() == 0) {
            tv_no_comments.setVisibility(View.VISIBLE);
            ll_comments.setVisibility(View.GONE);
            return;
        }

        int showSize = comments.size()>3?3:comments.size();
        for (int i = 0; i < showSize; i++) {
            Comment comment = comments.get(i);
            LinearLayout comment_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.comment_item, ll_comments, false);
            CircleImageView iv_userhead = (CircleImageView) comment_item.findViewById(R.id.iv_userhead);
            TextView tv_username = (TextView) comment_item.findViewById(R.id.tv_username);
            TextView tv_updatetime = (TextView) comment_item.findViewById(R.id.tv_updatetime);
            TextView tv_comments_likes = (TextView) comment_item.findViewById(R.id.tv_comments_likes);
            TextView tv_comments_replies = (TextView) comment_item.findViewById(R.id.tv_comments_replies);
            TextView tv_comment = (TextView) comment_item.findViewById(R.id.tv_comment);

            Image image = comment.getUser().getImages();
            if(image!=null && image.getAvatar()!=null){
                String userHeadUrl = StringUtil.removeBlank(image.getAvatar().getFull());
                Logger.d("UserHead:"+image.getAvatar().getFull()+"..."+image.getAvatar().getFull().contains(" "));
                ImageLoader.load(this,userHeadUrl,iv_userhead,R.drawable.default_userhead);
                Logger.d("1");
            }

            tv_username.setText(comment.getUser().getUsername());
            tv_updatetime.setText(comment.getUpdated_at());
            tv_comments_likes.setText(""+comment.getLikes());
            tv_comments_replies.setText(""+comment.getReplies());
            tv_comment.setText(comment.getComment());
            ll_comments.addView(comment_item);
        }
        Logger.d("时区："+TimeZone.getTimeZone("GMT")+"..."+TimeZone.getTimeZone("UTC")+"..."+TimeZone.getDefault().getDisplayName(false,TimeZone.SHORT)+"..."+TimeZone.getDefault().getDisplayName(false,TimeZone.LONG)+"..."+TimeZone.getDefault().getID());
    }
}
