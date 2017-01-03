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
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.entity.TmdbPeople;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;
import com.floatingmuseum.mocloud.ui.comments.SingleCommentActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
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
    LinearLayout commentContainer;
    @BindView(R.id.tv_no_comments)
    TextView tv_no_comments;
    @BindView(R.id.tv_comments_more)
    TextView tv_comments_more;

    private TmdbMovieDetail movie;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_moviedetail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        movie = getIntent().getParcelableExtra(MOVIE_OBJECT);
        presenter = new MovieDetailPresenter(this);

        actionBar.setTitle(movie.getTitle());
        initView();
        presenter.getData(movie.getId());
    }

    @Override
    protected void initView() {
        // TODO: 2017/1/3 还有TMDB评分可以显示
        actionBar.setTitle(movie.getTitle());
        tv_movie_title.setText(movie.getTitle());
        ImageLoader.load(this, StringUtil.buildPosterUrl(movie.getPoster_path()), iv_poster, R.drawable.default_movie_poster);
        tv_released.setText(movie.getRelease_date());
        tv_runtime.setText(movie.getRuntime() + " mins");
        tv_language.setText(movie.getOriginal_language());
        tv_overview.setText(movie.getOverview());
    }

    public void onBaseDataSuccess(TmdbMovieDetail movie) {
        Logger.d("数据获取成功...详情");
        this.movie = movie;
        // TODO: 2017/1/3  预算，收益，电影类型等可使用
        tv_runtime.setText(movie.getRuntime() + " mins");
    }

    public void onPeopleSuccess(TmdbPeople staffs) {
        Staff director = MoCloudUtil.getDirector(staffs.getCrew());
        if (director != null) {
            LinearLayout director_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_staff, ll_crew, false);
            setStaffClickListener(director_item, director);
            RatioImageView iv_staff_headshot = (RatioImageView) director_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) director_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) director_item.findViewById(R.id.tv_crew_realname);
            director_item.findViewById(R.id.tv_crew_character).setVisibility(View.GONE);
            ImageLoader.load(this, StringUtil.buildPosterUrl(director.getProfile_path()), iv_staff_headshot, R.drawable.default_movie_poster);
//            loadPeopleImage(director.getTmdbPeopleImage(), iv_staff_headshot);
            tv_crew_job.setText(director.getJob());
            tv_crew_realname.setText(director.getName());
            ll_crew.addView(director_item);
        }

        int showActorNum = director != null ? 3 : 4;
        List<Staff> casts = staffs.getCast();
        showActorNum = casts.size() > showActorNum ? showActorNum : casts.size();
        for (int i = 0; i < showActorNum; i++) {
            Staff cast = casts.get(i);
            LinearLayout actor_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_staff, ll_crew, false);
            setStaffClickListener(actor_item, cast);
            RatioImageView iv_staff_headshot = (RatioImageView) actor_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) actor_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) actor_item.findViewById(R.id.tv_crew_realname);
            TextView tv_crew_character = (TextView) actor_item.findViewById(R.id.tv_crew_character);
            ImageLoader.load(this, StringUtil.buildPosterUrl(cast.getProfile_path()), iv_staff_headshot, R.drawable.default_movie_poster);
//            loadPeopleImage(actor.getTmdbPeopleImage(), iv_staff_headshot);
            tv_crew_job.setText("Actor");
            tv_crew_realname.setText(cast.getName());
            tv_crew_character.setText(cast.getCharacter());
            ll_crew.addView(actor_item);
        }
    }

    public void onCommentsSuccess(final List<Comment> comments) {
        Logger.d("数据获取成功...评论");
        if (comments.size() == 0) {
            tv_no_comments.setVisibility(View.VISIBLE);
            commentContainer.setVisibility(View.GONE);
            return;
        }

        int showSize = comments.size() > 3 ? 3 : comments.size();
        if (comments.size() > 3) {
            tv_comments_more.setVisibility(View.VISIBLE);
            tv_comments_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailActivity.this, CommentsActivity.class);
                    intent.putExtra(CommentsActivity.MOVIE_OBJECT, movie);
                    startActivity(intent);
                }
            });
        }
        for (int i = 0; i < showSize; i++) {
            final Comment comment = comments.get(i);
            LinearLayout comment_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.comment_item, commentContainer, false);
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

            String name = comment.getUser().getName();
            if (name != null && name.length() > 0) {
                tv_username.setText(name);
            } else {
                tv_username.setText(comment.getUser().getUsername());
            }
            tv_updatetime.setText(TimeUtil.formatGmtTime(comment.getUpdated_at()));
            tv_comments_likes.setText("" + comment.getLikes());
            tv_comments_replies.setText("" + comment.getReplies());
            tv_comment.setText(comment.getComment());
            tv_comments_likes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d("评论ID：" + comment.getId() + "...");
                }
            });

            comment_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailActivity.this, SingleCommentActivity.class);
                    intent.putExtra(SingleCommentActivity.MAIN_COMMENT, comment);
                    startActivity(intent);
                }
            });

            iv_userhead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openUserActivity(MovieDetailActivity.this, comment.getUser());
                }
            });
            commentContainer.addView(comment_item, i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
    }
}
