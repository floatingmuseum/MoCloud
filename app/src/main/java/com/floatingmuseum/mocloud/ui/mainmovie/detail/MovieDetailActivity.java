package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.base.BaseDetailActivity;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Actor;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.People;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.NumberFormatUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class MovieDetailActivity extends BaseActivity implements BaseDetailActivity {
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_OBJECT = "movie_object";

    private MovieDetailPresenter presenter;

    @BindView(R.id.poster)
    RatioImageView iv_poster;
    @BindView(R.id.tv_movie_title)
    TextView tv_movie_title;
    @BindView(R.id.tv_released)
    TextView tv_released;
    @BindView(R.id.tv_runtime)
    TextView tv_runtime;
    @BindView(R.id.tv_language)
    TextView tv_language;
    @BindView(R.id.tv_rating)
    TextView tv_rating;
    @BindView(R.id.tv_overview)
    TextView tv_overview;
    @BindView(R.id.ll_crew)
    LinearLayout ll_crew;
    @BindView(R.id.ll_comments)
    LinearLayout ll_comments;
    @BindView(R.id.tv_no_comments)
    TextView tv_no_comments;
    @BindView(R.id.tv_comments_more)
    TextView tv_comments_more;
    private Movie movie;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_moviedetail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        movie = (Movie) getIntent().getSerializableExtra(MOVIE_OBJECT);
//        movieID = getIntent().getStringExtra(MOVIE_ID);
//        movieTitle = getIntent().getStringExtra(MOVIE_TITLE);
        Logger.d("电影:" + movie.getTitle());
        actionBar.setTitle(movie.getTitle());

        presenter = new MovieDetailPresenter(this, Repository.getInstance());

        initBaseData(movie);
        presenter.getData(movie);
    }

    private void initBaseData(Movie movie) {
        String tmdbPosterUrl = "https://image.tmdb.org/t/p/w185" + movie.getImage().getPosters().get(0).getFile_path();
        ImageLoader.load(this, tmdbPosterUrl, iv_poster, R.drawable.default_movie_poster);
        tv_movie_title.setText(movie.getTitle());
        tv_released.setText(movie.getReleased());
        tv_runtime.setText(movie.getRuntime() + " mins");
        tv_language.setText(movie.getLanguage());
        Double rating = movie.getRating();
        tv_rating.setText(NumberFormatUtil.doubleFormatToString(rating, false, 2));
        tv_overview.setText(movie.getOverview());
    }

    @Override
    protected void initView() {

    }

    public void onBaseDataSuccess(Movie movie) {
//        ImageLoader.load(this, movie.getImages().getPoster().getThumb(), iv_poster,R.drawable.default_movie_poster);

//        ImageLoader.load(this, null, iv_poster, R.drawable.default_movie_poster);
//        tv_movie_title.setText(movie.getTitle());
//        tv_released.setText(movie.getReleased());
//        tv_runtime.setText(movie.getRuntime() + " mins");
//        tv_language.setText(movie.getLanguage());
//        Double rating = movie.getRating();
//        tv_rating.setText(NumberFormatUtil.doubleFormatToString(rating, false, 2));
//        tv_overview.setText(movie.getOverview());
    }

    public void onPeopleSuccess(TmdbPeople people) {
        TmdbPeople.Crew director = null;
        for (TmdbPeople.Crew crew : people.getCrew()) {
            if (crew.getJob().equals("Director")) {
                director = crew;
                break;
            }
        }
//        Staff director = people.getCrew();
        if (director != null) {
            LinearLayout staff = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.staff_item, ll_crew, false);
            RatioImageView iv_staff_headshot = (RatioImageView) staff.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) staff.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) staff.findViewById(R.id.tv_crew_realname);
            staff.findViewById(R.id.tv_crew_character).setVisibility(View.GONE);
//            ImageLoader.load(this, director.getPerson().getImages().getHeadshot().getThumb(), iv_staff_headshot, R.drawable.default_movie_poster);
            ImageLoader.load(this, StringUtil.buildPeopleHeadshotUrl(director.getProfile_path()), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText(director.getJob());
//            tv_crew_realname.setText(director.getPerson().getName());
            tv_crew_realname.setText(director.getName());
            ll_crew.addView(staff);
        }

        List<TmdbPeople.Cast> actors = people.getCast();
//        List<Actor> actors = people.getCast();
        int showSize = actors.size() > 5 ? 5 : actors.size();

        for (int i = 0; i < showSize; i++) {
            TmdbPeople.Cast actor = actors.get(i);
            LinearLayout staff_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.staff_item, ll_crew, false);
            RatioImageView iv_staff_headshot = (RatioImageView) staff_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) staff_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) staff_item.findViewById(R.id.tv_crew_realname);
            TextView tv_crew_character = (TextView) staff_item.findViewById(R.id.tv_crew_character);
            ImageLoader.load(this, StringUtil.buildPeopleHeadshotUrl(actor.getProfile_path()), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText("Actor");
            tv_crew_realname.setText(actor.getName());
            tv_crew_character.setText(actor.getCharacter());
            ll_crew.addView(staff_item);
        }
    }

    public void onCommentsSuccess(final List<Comment> comments) {
        if (comments.size() == 0) {
            tv_no_comments.setVisibility(View.VISIBLE);
            ll_comments.setVisibility(View.GONE);
            return;
        }

        int showSize = comments.size() > 3 ? 3 : comments.size();
        if (comments.size() > 3) {
            tv_comments_more.setVisibility(View.VISIBLE);
            tv_comments_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailActivity.this, CommentsActivity.class);
                    intent.putExtra(CommentsActivity.MOVIE_ID, movie.getIds().getSlug());
                    intent.putExtra(CommentsActivity.MOVIE_TITLE, movie.getTitle());
                    startActivity(intent);
                }
            });
        }
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
            if (image != null && image.getAvatar() != null) {
                ImageLoader.loadDontAnimate(this, image.getAvatar().getFull(), iv_userhead, R.drawable.default_userhead);
            }

            tv_username.setText(comment.getUser().getUsername());
            tv_updatetime.setText(TimeUtil.formatGmtTime(comment.getUpdated_at()));
            tv_comments_likes.setText("" + comment.getLikes());
            tv_comments_replies.setText("" + comment.getReplies());
            tv_comment.setText(comment.getComment());
            ll_comments.addView(comment_item);
        }
    }
}
