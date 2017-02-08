package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
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
import com.floatingmuseum.mocloud.utils.ColorUtil;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
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
    RatioImageView ivPoster;
    @BindView(R.id.tv_movie_title)
    TextView tvMovieTitle;
    @BindView(R.id.tv_released)
    TextView tvReleased;
    @BindView(R.id.tv_runtime)
    TextView tvRuntime;
    @BindView(R.id.tv_language)
    TextView tvLanguage;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.tv_overview)
    TextView tvOverview;
    @BindView(R.id.ll_crew)
    LinearLayout llCrew;
    @BindView(R.id.ll_comments)
    LinearLayout commentContainer;
    @BindView(R.id.tv_no_more_comments)
    TextView tvNoMoreComments;
    @BindView(R.id.tv_comments_more)
    TextView tvCommentsMore;

    private Movie movie;
    private MovieDetailPresenter presenter;
    private CheckBox isSpoiler;
    private EditText commentBox;
    private LinearLayout llCommentsReply;
    private Staff staff;
    private Palette.Swatch detailSwatch;
    private Palette.Swatch commentItemSwatch;


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
//        Logger.d("电影名onCreate:" + movie.getTitle() + "..." + movie.getId());
        presenter.getData(movie);
        initColors();
        initView();
    }

    private void initColors() {
        final LinearLayout llMovie = (LinearLayout) findViewById(R.id.ll_movie_container);
        final TextView tvMovieTitleText = (TextView) findViewById(R.id.tv_movie_title_text);
        final TextView tvReleasedTitleText = (TextView) findViewById(R.id.tv_released_title_text);
        final TextView tvRuntimeTitleText = (TextView) findViewById(R.id.tv_runtime_title_text);
        final TextView tvLanguageTitleText = (TextView) findViewById(R.id.tv_language_title_text);
        final TextView tvRatingTitleText = (TextView) findViewById(R.id.tv_rating_title_text);
        final TextView tvOverviewTitle = (TextView) findViewById(R.id.tv_overview_title);
        final TextView tvCrewTitle = (TextView) findViewById(R.id.tv_crew_title);
        final TextView tvCommentsTitle = (TextView) findViewById(R.id.tv_comments_title);

        Glide.with(this)
                .load(movie.getImage().getCacheFile())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch lightMuteSwatch = palette.getLightMutedSwatch();
                                Palette.Swatch lightVibrantSwatch = palette.getLightVibrantSwatch();

                                Palette.Swatch muteSwatch = palette.getMutedSwatch();
                                Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();

                                Palette.Swatch darkMuteSwatch = palette.getDarkMutedSwatch();
                                Palette.Swatch darkVibrantSwatch = palette.getDarkVibrantSwatch();

                                Logger.d("Palette...muteSwatch:" + (muteSwatch != null) + "...vibrantSwatch:" + (vibrantSwatch != null));
                                Logger.d("Palette...lightMuteSwatch:" + (lightMuteSwatch != null) + "...lightVibrantSwatch:" + (lightVibrantSwatch != null));
                                Logger.d("Palette...darkMuteSwatch:" + (darkMuteSwatch != null) + "...darkVibrantSwatch:" + (darkVibrantSwatch != null));

                                if (lightMuteSwatch != null && muteSwatch != null) {
                                    commentItemSwatch = lightMuteSwatch;
                                    detailSwatch = muteSwatch;
                                } else if (darkVibrantSwatch != null && vibrantSwatch != null) {
                                    commentItemSwatch = darkVibrantSwatch;
                                    detailSwatch = vibrantSwatch;
                                }

                                if (enableColorful()) {
                                    if (android.os.Build.VERSION.SDK_INT >= 21) {
                                        Window window = getWindow();
                                        window.setStatusBarColor(ColorUtil.darkerColor(detailSwatch.getRgb(), 0.4));
                                        window.setNavigationBarColor(ColorUtil.darkerColor(detailSwatch.getRgb(), 0.4));
                                    }

                                    toolbar.setBackgroundColor(ColorUtil.darkerColor(detailSwatch.getRgb(), 0.2));
                                    llMovie.setBackgroundColor(detailSwatch.getRgb());

                                    int bodyTextColor = detailSwatch.getBodyTextColor();
                                    detailSwatch.getTitleTextColor();
                                    tvMovieTitle.setTextColor(bodyTextColor);
                                    tvReleased.setTextColor(bodyTextColor);
                                    tvRuntime.setTextColor(bodyTextColor);
                                    tvLanguage.setTextColor(bodyTextColor);
                                    tvOverview.setTextColor(bodyTextColor);
                                    tvRating.setTextColor(bodyTextColor);

                                    tvTomatoRating.setTextColor(bodyTextColor);
                                    tvTomatoRatingCount.setTextColor(bodyTextColor);
                                    tvTraktRating.setTextColor(bodyTextColor);
                                    tvTraktRatingCount.setTextColor(bodyTextColor);
                                    tvImdbRating.setTextColor(bodyTextColor);
                                    tvImdbRatingCount.setTextColor(bodyTextColor);
                                    tvOverview.setTextColor(bodyTextColor);

                                    int titleTextColor = detailSwatch.getTitleTextColor();
                                    tvMovieTitleText.setTextColor(titleTextColor);
                                    tvReleasedTitleText.setTextColor(titleTextColor);
                                    tvRuntimeTitleText.setTextColor(titleTextColor);
                                    tvLanguageTitleText.setTextColor(titleTextColor);
                                    tvRatingTitleText.setTextColor(titleTextColor);
                                    tvOverviewTitle.setTextColor(titleTextColor);
                                    tvCrewTitle.setTextColor(titleTextColor);
                                    tvCommentsTitle.setTextColor(titleTextColor);
                                }
                            }
                        });
                    }
                });
    }

    @Override
    protected void initView() {
        actionBar.setTitle(movie.getTitle());
        tvMovieTitle.setText(movie.getTitle());
        tvReleased.setText(movie.getReleased());
        tvRuntime.setText(movie.getRuntime() + " mins");
        tvLanguage.setText(movie.getLanguage());
        tvOverview.setText(movie.getOverview());
        tvRating.setText(NumberFormatUtil.doubleFormatToString(movie.getRating(), false, 2) + "/" + movie.getVotes() + "votes");
        tvTraktRating.setText(NumberFormatUtil.doubleFormatToString(movie.getRating(), false, 2));
        tvTraktRatingCount.setText(movie.getVotes() + "votes");
        ImageLoader.loadPoster(this, ivPoster, movie, R.drawable.default_movie_poster);
    }

    public void onMovieTeamSuccess(MovieTeam movieTeam) {
        List<Staff> detailShowList = movieTeam.getDetailShowList();
        if (detailShowList == null || detailShowList.size() == 0) {
            return;
        }

        if (detailShowList.get(0).getJob() != null) {
            Staff director = detailShowList.get(0);
            LinearLayout director_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_staff, llCrew, false);
            setStaffClickListener(director_item, director);
            RatioImageView iv_staff_headshot = (RatioImageView) director_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) director_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) director_item.findViewById(R.id.tv_crew_realname);
            director_item.findViewById(R.id.tv_crew_character).setVisibility(View.GONE);
            ImageLoader.loadFromTmdbPersonImage(this, director.getTmdbPersonImage(), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText(director.getJob());
            tv_crew_realname.setText(director.getPerson().getName());
            if (enableColorful()) {
                tv_crew_job.setTextColor(detailSwatch.getBodyTextColor());
                tv_crew_realname.setTextColor(detailSwatch.getBodyTextColor());
            }
            llCrew.addView(director_item);
        }

        int x = detailShowList.get(0).getJob() != null ? 1 : 0;
        for (int i = x; i < detailShowList.size(); i++) {
            Staff cast = detailShowList.get(i);
            LinearLayout actor_item = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.item_staff, llCrew, false);
            setStaffClickListener(actor_item, cast);
            RatioImageView iv_staff_headshot = (RatioImageView) actor_item.findViewById(R.id.iv_staff_headshot);
            TextView tv_crew_job = (TextView) actor_item.findViewById(R.id.tv_crew_job);
            TextView tv_crew_realname = (TextView) actor_item.findViewById(R.id.tv_crew_realname);
            TextView tv_crew_character = (TextView) actor_item.findViewById(R.id.tv_crew_character);
            ImageLoader.loadFromTmdbPersonImage(this, cast.getTmdbPersonImage(), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText("Actor");
            tv_crew_realname.setText(cast.getPerson().getName());
            tv_crew_character.setText(cast.getCharacter());
            if (enableColorful()) {
                tv_crew_job.setTextColor(detailSwatch.getBodyTextColor());
                tv_crew_realname.setTextColor(detailSwatch.getBodyTextColor());
                tv_crew_character.setTextColor(detailSwatch.getBodyTextColor());
            }
            llCrew.addView(actor_item);
        }
    }


    private boolean enableColorful() {
        if (commentItemSwatch != null && detailSwatch != null) {
            return true;
        } else {
            return false;
        }
    }

    public void onCommentsSuccess(final List<Comment> comments) {
        // TODO: 2017/1/9 Sync likes,添加回复当回复数不足3个时
        Logger.d("数据获取成功...评论");

        int showSize = comments.size() > 3 ? 3 : comments.size();
        if (comments.size() > 3) {
            tvCommentsMore.setVisibility(View.VISIBLE);
            tvCommentsMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailActivity.this, CommentsActivity.class);
                    intent.putExtra(CommentsActivity.MOVIE_OBJECT, movie);
                    startActivity(intent);
                }
            });
        } else {
            tvNoMoreComments.setVisibility(View.VISIBLE);
            tvNoMoreComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d("tv_no_more_comments...点击");
                    inflateCommentLayout();
                }
            });
        }

        if (enableColorful()) {
            tvCommentsMore.setTextColor(detailSwatch.getTitleTextColor());
            tvNoMoreComments.setTextColor(detailSwatch.getTitleTextColor());
        }

        for (int i = 0; i < showSize; i++) {
            final Comment comment = comments.get(i);
            CardView comment_item = buildCommentItem(comment);
            commentContainer.addView(comment_item);
        }
    }

    private CardView buildCommentItem(final Comment comment) {
        CardView comment_item = (CardView) LayoutInflater.from(this).inflate(R.layout.comment_item, commentContainer, false);
        if (enableColorful()) {
            initCommentItem(this, comment_item, comment, commentItemSwatch, false);
        } else {
            initCommentItem(this, comment_item, comment, null, false);
        }
        return comment_item;
    }

    private void inflateCommentLayout() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        Logger.d("tv_no_more_comments...");
        llCommentsReply = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.comment_layout, commentContainer, false);
        isSpoiler = (CheckBox) llCommentsReply.findViewById(R.id.is_spoiler);
        commentBox = (EditText) llCommentsReply.findViewById(R.id.comment_box);
        ImageView ivReply = (ImageView) llCommentsReply.findViewById(R.id.iv_reply);
        movieDetailContainer.addView(llCommentsReply);
        tvNoMoreComments.setVisibility(View.GONE);
        svMovieDetail.fullScroll(View.FOCUS_DOWN);

        if (enableColorful()) {
            llCommentsReply.setBackgroundColor(ColorUtil.darkerColor(detailSwatch.getRgb(), 0.1));
        }

        ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
        KeyboardUtil.showSoftInput(commentBox);
    }

    private void sendComment() {
        String replyContent = commentBox.getText().toString();
        Logger.d("回复内容:" + replyContent + "...isSpoiler" + isSpoiler.isChecked());
        if (!StringUtil.checkReplyContent(replyContent)) {
            ToastUtil.showToast(R.string.comment_tip1);
            return;
        }
        Comment comment = new Comment();
        comment.setSpoiler(isSpoiler.isChecked());
        comment.setComment(replyContent);
        presenter.sendComment(comment, movie.getIds().getSlug());
    }

    public void onSendCommentSuccess(Comment comment) {
        CardView comment_item = buildCommentItem(comment);
        ToastUtil.showToast(R.string.comment_success);
        resetCommentBox(commentBox, isSpoiler);
//        KeyboardUtil.hideSoftInput(this);
//        comment_box.setText("");
//        is_spoiler.setChecked(false);
        if (commentContainer.getChildCount() == 3) {
            tvCommentsMore.setVisibility(View.VISIBLE);
            llCommentsReply.setVisibility(View.GONE);
            return;
        }
        commentContainer.addView(comment_item);
    }

    public void onTraktRatingsSuccess(Ratings ratings) {
        Logger.d("TraktRating:" + ratings.getRating() + "..." + ratings.getVotes());
        tvRating.setText(NumberFormatUtil.doubleFormatToString(ratings.getRating(), false, 2) + "/" + ratings.getVotes() + "votes");
        tvTraktRating.setText(NumberFormatUtil.doubleFormatToString(ratings.getRating(), false, 2));
        tvTraktRatingCount.setText(ratings.getVotes() + "votes");
    }

    public void onOtherRatingsSuccess(OmdbInfo omdbInfo) {
        Logger.d("ImdbRating:" + omdbInfo.getImdbRating() + "..." + omdbInfo.getImdbVotes() + "...tomatoesRating:" + omdbInfo.getTomatoUserRating() + "..." + omdbInfo.getTomatoUserReviews());
        String imdbRating = omdbInfo.getImdbRating();
        tvImdbRating.setText(imdbRating == null ? "N/A" : imdbRating);
        String imdbVotes = omdbInfo.getImdbVotes();
        imdbVotes = imdbVotes == null ? "N/A" : omdbInfo.getImdbVotes().replace(",", "") + "votes";
        tvImdbRatingCount.setText(imdbVotes);
        llImdbRating.setVisibility(View.VISIBLE);

        String tomatoUserRating = omdbInfo.getTomatoUserRating();
        double tomatoRating = Double.valueOf(tomatoUserRating);
        if (tomatoRating < 3.5) {
            Glide.with(this).load(R.drawable.popcorn_bad).into(ivTomatoPopcornState);
        }
        tvTomatoRating.setText(tomatoUserRating);
        tvTomatoRatingCount.setText(omdbInfo.getTomatoUserReviews() + "votes");
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
