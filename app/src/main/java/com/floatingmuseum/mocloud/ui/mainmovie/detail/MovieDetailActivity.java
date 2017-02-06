package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseCommentsActivity;
import com.floatingmuseum.mocloud.base.BaseDetailActivity;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.NumberFormatUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Floatingmuseum on 2016/6/20.
 */
public class MovieDetailActivity extends BaseCommentsActivity implements BaseDetailActivity {
    public static final String MOVIE_OBJECT = "movie_object";
//    public static final String MOVIE_OBJECT_TRAKT= "movie_object_trakt";

    @BindView(R.id.sv_movie_detail)
    ScrollView svMovieDetail;
    @BindView(R.id.ll_movie_detail)
    LinearLayout movieDetailContainer;
    @BindView(R.id.iv_tomato_popcorn_state)
    ImageView ivTomatoPopcornState;
    @BindView(R.id.tv_tomato_rating)
    TextView tvTomatoRating;
    @BindView(R.id.tv_tomato_rating_count)
    TextView tvTomatoRatingCount;
    @BindView(R.id.ll_tomato_rating)
    LinearLayout llTomatoRating;
    @BindView(R.id.tv_trakt_rating)
    TextView tvTraktRating;
    @BindView(R.id.tv_trakt_rating_count)
    TextView tvTraktRatingCount;
    @BindView(R.id.ll_trakt_rating)
    LinearLayout llTraktRating;
    @BindView(R.id.tv_imdb_rating)
    TextView tvImdbRating;
    @BindView(R.id.tv_imdb_rating_count)
    TextView tvImdbRatingCount;
    @BindView(R.id.ll_imdb_rating)
    LinearLayout llImdbRating;
    @BindView(R.id.ll_ratings)
    LinearLayout llRatings;

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
    @BindView(R.id.tv_no_more_comments)
    TextView tv_no_more_comments;
    @BindView(R.id.tv_comments_more)
    TextView tv_comments_more;

    //    private TmdbMovieDetail movie;
    private Movie movie_object;
    private MovieDetailPresenter presenter;
    private CheckBox is_spoiler;
    private EditText comment_box;
    private LinearLayout ll_comments_reply;
    private Staff staff;


    @Override
    protected int currentLayoutId() {
        return R.layout.activity_moviedetail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        movie_object = getIntent().getParcelableExtra(MOVIE_OBJECT);
        presenter = new MovieDetailPresenter(this);
//        Logger.d("电影名onCreate:" + movie.getTitle() + "..." + movie.getId());
        presenter.getData(movie_object);
//        initView();
        initView();
    }

    @Override
    protected void initView() {
        actionBar.setTitle(movie_object.getTitle());
        tv_movie_title.setText(movie_object.getTitle());
        tv_released.setText(movie_object.getReleased());
        tv_runtime.setText(movie_object.getRuntime() + " mins");
        tv_language.setText(movie_object.getLanguage());
        tv_overview.setText(movie_object.getOverview());
        tv_rating.setText(NumberFormatUtil.doubleFormatToString(movie_object.getRating(), false, 2) + "/" + movie_object.getVotes() + "votes");
        tvTraktRating.setText(NumberFormatUtil.doubleFormatToString(movie_object.getRating(), false, 2));
        tvTraktRatingCount.setText(movie_object.getVotes() + "votes");
        ImageLoader.loadPoster(this, iv_poster, movie_object, R.drawable.default_movie_poster);
    }

//    @Override
//    protected void initView() {
//        // TODO: 2017/1/9 Sync watched,collocted,rating,history   watching now user,related movies
//        actionBar.setTitle(movie.getTitle());
//        tv_movie_title.setText(movie.getTitle());
//        tv_released.setText(movie.getRelease_date());
//        if (movie.isFromStaffWorks()) {
//            ImageLoader.load(this, StringUtil.buildPosterUrl(movie.getPoster_path()), iv_poster, R.drawable.default_movie_poster);
//            return;
//        }
//        tv_runtime.setText(movie.getRuntime() + " mins");
//        tv_language.setText(movie.getOriginal_language());
//        tv_overview.setText(movie.getOverview());
//        if (movie.isTraktItem()) {
//            tv_rating.setText(NumberFormatUtil.doubleFormatToString(movie.getTraktRatings(), false, 2) + "/" + movie.getTraktVotes() + "votes");
//            tvTraktRating.setText(NumberFormatUtil.doubleFormatToString(movie.getTraktRatings(), false, 2));
//            tvTraktRatingCount.setText(movie.getTraktVotes() + "votes");
//            ImageLoader.loadFromTmdbMovieImage(this, movie.getImage(), iv_poster, R.drawable.default_movie_poster);
//            return;
//        }
//        ImageLoader.load(this, StringUtil.buildPosterUrl(movie.getPoster_path()), iv_poster, R.drawable.default_movie_poster);
//        tvTmdbRating.setText(String.valueOf(movie.getVote_average()));
//        tvTmdbRatingCount.setText(movie.getVote_count() + "votes");
//    }

//    public void onBaseDataSuccess(TmdbMovieDetail movie) {
//        Logger.d("数据获取成功...详情");
//        this.movie = movie;
//        // TODO: 2017/1/3  预算，收益，电影类型等可使用
//        tv_runtime.setText(movie.getRuntime() + " mins");
//        tv_language.setText(movie.getOriginal_language());
//        tv_overview.setText(movie.getOverview());
//        tvTmdbRating.setText(String.valueOf(movie.getVote_average()));
//        tvTmdbRatingCount.setText(movie.getVote_count() + "votes");
//    }

    public void onMovieTeamSuccess(MovieTeam movieTeam) {
        List<Staff> detailShowList = movieTeam.getDetailShowList();
        if (detailShowList == null || detailShowList.size() == 0) {
            return;
        }

        if (detailShowList.get(0).getJob() != null) {
            Staff director = detailShowList.get(0);
            LinearLayout director_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_staff, ll_crew, false);
            setStaffClickListener(director_item, director);
            RatioImageView iv_staff_headshot = (RatioImageView) director_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) director_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) director_item.findViewById(R.id.tv_crew_realname);
            director_item.findViewById(R.id.tv_crew_character).setVisibility(View.GONE);
            ImageLoader.loadFromTmdbPersonImage(this, director.getTmdbPersonImage(), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText(director.getJob());
            tv_crew_realname.setText(director.getPerson().getName());
            ll_crew.addView(director_item);
        }

        int x = detailShowList.get(0).getJob() != null ? 1 : 0;
        for (int i = x; i < detailShowList.size(); i++) {
            Staff cast = detailShowList.get(i);
            LinearLayout actor_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_staff, ll_crew, false);
            setStaffClickListener(actor_item, cast);
            RatioImageView iv_staff_headshot = (RatioImageView) actor_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) actor_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) actor_item.findViewById(R.id.tv_crew_realname);
            TextView tv_crew_character = (TextView) actor_item.findViewById(R.id.tv_crew_character);
            ImageLoader.loadFromTmdbPersonImage(this, cast.getTmdbPersonImage(), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText("Actor");
            tv_crew_realname.setText(cast.getPerson().getName());
            tv_crew_character.setText(cast.getCharacter());
            ll_crew.addView(actor_item);
        }
    }

    public void onCommentsSuccess(final List<Comment> comments) {
        // TODO: 2017/1/9 Sync likes,添加回复当回复数不足3个时
        Logger.d("数据获取成功...评论");

        int showSize = comments.size() > 3 ? 3 : comments.size();
        if (comments.size() > 3) {
            tv_comments_more.setVisibility(View.VISIBLE);
            tv_comments_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent intent = new Intent(MovieDetailActivity.this, CommentsActivity.class);
//                    intent.putExtra(CommentsActivity.MOVIE_OBJECT, movie);
//                    startActivity(intent);
                }
            });
        } else {
            tv_no_more_comments.setVisibility(View.VISIBLE);
            tv_no_more_comments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d("tv_no_more_comments...点击");
                    inflateCommentLayout();
                }
            });
        }
        for (int i = 0; i < showSize; i++) {
            final Comment comment = comments.get(i);
            CardView comment_item = buildCommentItem(comment);
            commentContainer.addView(comment_item);
        }
    }

    private CardView buildCommentItem(final Comment comment) {
        CardView comment_item = (CardView) LayoutInflater.from(this).inflate(R.layout.comment_item, commentContainer, false);
        initCommentItem(this, comment_item, comment, false);
        return comment_item;
    }

    private void inflateCommentLayout() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        Logger.d("tv_no_more_comments...");
        ll_comments_reply = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.comment_layout, commentContainer, false);
        is_spoiler = (CheckBox) ll_comments_reply.findViewById(R.id.is_spoiler);
        comment_box = (EditText) ll_comments_reply.findViewById(R.id.comment_box);
        ImageView iv_reply = (ImageView) ll_comments_reply.findViewById(R.id.iv_reply);
        movieDetailContainer.addView(ll_comments_reply);
        tv_no_more_comments.setVisibility(View.GONE);
        svMovieDetail.fullScroll(View.FOCUS_DOWN);

        iv_reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        KeyboardUtil.showSoftInput(comment_box);
    }

    private void sendComment() {
        String replyContent = comment_box.getText().toString();
        Logger.d("回复内容:" + replyContent + "...isSpoiler" + is_spoiler.isChecked());
        if (!StringUtil.checkReplyContent(replyContent)) {
            ToastUtil.showToast(R.string.comment_tip1);
            return;
        }
        Comment comment = new Comment();
        comment.setSpoiler(is_spoiler.isChecked());
        comment.setComment(replyContent);
        presenter.sendComment(comment, movie_object.getIds().getSlug());
    }

    public void onSendCommentSuccess(Comment comment) {
        CardView comment_item = buildCommentItem(comment);
        ToastUtil.showToast(R.string.comment_success);
        resetCommentBox(comment_box, is_spoiler);
//        KeyboardUtil.hideSoftInput(this);
//        comment_box.setText("");
//        is_spoiler.setChecked(false);
        if (commentContainer.getChildCount() == 3) {
            tv_comments_more.setVisibility(View.VISIBLE);
            ll_comments_reply.setVisibility(View.GONE);
            return;
        }
        commentContainer.addView(comment_item);
    }

    public void onTraktRatingsSuccess(Ratings ratings) {
        Logger.d("TraktRating:" + ratings.getRating() + "..." + ratings.getVotes());
        tv_rating.setText(NumberFormatUtil.doubleFormatToString(ratings.getRating(), false, 2) + "/" + ratings.getVotes() + "votes");
        tvTraktRating.setText(NumberFormatUtil.doubleFormatToString(ratings.getRating(), false, 2));
        tvTraktRatingCount.setText(ratings.getVotes() + "votes");
    }

    public void onOtherRatingsSuccess(OmdbInfo omdbInfo) {
        Logger.d("ImdbRating:" + omdbInfo.getImdbRating() + "..." + omdbInfo.getImdbVotes()+"...tomatoesRating:"+omdbInfo.getTomatoUserRating()+"..."+omdbInfo.getTomatoUserReviews());
        String imdbRating = omdbInfo.getImdbRating();
        tvImdbRating.setText(imdbRating == null ? "N/A" : imdbRating);
        String imdbVotes = omdbInfo.getImdbVotes();
        imdbVotes = imdbVotes == null ? "N/A" : omdbInfo.getImdbVotes().replace(",", "") + "votes";
        tvImdbRatingCount.setText(imdbVotes);
        llImdbRating.setVisibility(View.VISIBLE);

        String tomatoUserRating = omdbInfo.getTomatoUserRating();
        double tomatoRating = Double.valueOf(tomatoUserRating);
        if (tomatoRating<3.5){
            Glide.with(this).load(R.drawable.popcorn_bad).into(ivTomatoPopcornState);
        }
        tvTomatoRating.setText(tomatoUserRating);
        tvTomatoRatingCount.setText(omdbInfo.getTomatoUserReviews()+"votes");
        llTomatoRating.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
    }
}
