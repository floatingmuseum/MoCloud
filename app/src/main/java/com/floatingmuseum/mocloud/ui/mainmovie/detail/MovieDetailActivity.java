package com.floatingmuseum.mocloud.ui.mainmovie.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseCommentsActivity;
import com.floatingmuseum.mocloud.base.BaseDetailActivity;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieCollection;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatched;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatchlist;
import com.floatingmuseum.mocloud.data.entity.Colors;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.SyncData;
import com.floatingmuseum.mocloud.data.entity.Ids;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieSyncItem;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.ui.comments.CommentsActivity;
import com.floatingmuseum.mocloud.utils.ColorUtil;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.NumberFormatUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.google.android.youtube.player.YouTubeIntents;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmModel;
import mehdi.sakout.fancybuttons.FancyButton;


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
    @BindView(R.id.ll_sync)
    LinearLayout llSync;
    @BindView(R.id.fb_watched)
    FancyButton fbWatched;
    @BindView(R.id.fb_watchlist)
    FancyButton fbWatchlist;
    @BindView(R.id.fb_collection)
    FancyButton fbCollection;
//    @BindView(R.id.tv_sync_watchlist)
//    TextView tvSyncWatchlist;
//    @BindView(R.id.tv_sync_watched)
//    TextView tvSyncWatched;
//    @BindView(R.id.tv_sync_collection)
//    TextView tvSyncCollection;

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
    @BindView(R.id.tv_tagline_title)
    TextView tvTaglineTitle;
    @BindView(R.id.tv_tagline)
    TextView tvTagline;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tv_movie_title_text)
    TextView tvMovieTitleText;
    @BindView(R.id.tv_released_title_text)
    TextView tvReleasedTitleText;
    @BindView(R.id.tv_runtime_title_text)
    TextView tvRuntimeTitleText;
    @BindView(R.id.tv_language_title_text)
    TextView tvLanguageTitleText;
    @BindView(R.id.show_youtube)
    Button showYoutube;

    @BindView(R.id.ll_movie_header)
    LinearLayout llMovieHeader;
    @BindView(R.id.tv_overview_title)
    TextView tvOverviewTitle;
    @BindView(R.id.tv_crew_title)
    TextView tvCrewTitle;
    @BindView(R.id.tv_comments_title)
    TextView tvCommentsTitle;
    @BindView(R.id.ll_movie_container)
    LinearLayout llMovieContainer;
    @BindView(R.id.tv_certification_title_text)
    TextView tvCertificationTitleText;
    @BindView(R.id.tv_certification)
    TextView tvCertification;
    @BindView(R.id.tv_genres_title_text)
    TextView tvGenresTitleText;
    @BindView(R.id.tv_genres)
    TextView tvGenres;
    @BindView(R.id.avl_crew)
    AVLoadingIndicatorView avlCrew;
    @BindView(R.id.avl_comments)
    AVLoadingIndicatorView avlComments;

    private Movie movie;
    private MovieDetailPresenter presenter;
    private CheckBox isSpoiler;
    private EditText commentBox;
    private LinearLayout llCommentsReply;
    private Staff staff;
    private Palette.Swatch itemSwatch;
    private Palette.Swatch mainSwatch;
    private boolean hasWatched = false;
    private boolean hasWatchlist = false;
    private boolean hasCollected = false;
    private String nowWatchedTime;
    private String nowCollectedTime;


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
        presenter.getData(movie);
        initView();
        loadUserData();
    }

    private void loadUserData() {
        if (SPUtil.isLogin()) {
            presenter.loadUserData(movie.getIds().getTrakt());
        }
    }

    @Override
    protected void initView() {
        initLoginView();
        avlComments.smoothToShow();
        avlCrew.smoothToShow();
//        ImageLoader.load(this, movie.getImage().getBitmap(), ivPoster, R.drawable.default_movie_poster);
        Bitmap bitmap = movie.getImage().getBitmap();
        initColors(bitmap);
        if (bitmap != null) {
            ivPoster.setImageBitmap(movie.getImage().getBitmap());
        } else {
            ivPoster.setImageResource(R.drawable.default_movie_poster);
        }
        actionBar.setTitle(movie.getTitle());
        tvMovieTitle.setText(movie.getTitle());
        tvReleased.setText(movie.getReleased());
        tvRuntime.setText(movie.getRuntime() + " mins");
        tvLanguage.setText(movie.getLanguage());
        tvOverview.setText(movie.getOverview());
        String tagline = movie.getTagline();
        if (StringUtil.hasData(tagline)) {
            tvTagline.setText(tagline);
        } else {
            tvTaglineTitle.setVisibility(View.GONE);
            tvTagline.setVisibility(View.GONE);
        }
        if (movie.getCertification() != null && movie.getCertification().length() > 0) {
            tvCertificationTitleText.setVisibility(View.VISIBLE);
            tvCertification.setVisibility(View.VISIBLE);
            tvCertification.setText(movie.getCertification());
        } else {
            List<String> genres = movie.getGenres();
            if (genres != null && genres.size() > 0) {
                StringBuffer sbGenre = new StringBuffer();
                for (int i = 0; i < genres.size(); i++) {
                    if (i == (genres.size() - 1)) {
                        sbGenre.append(genres.get(i));
                    } else {
                        sbGenre.append(genres.get(i) + ",");
                    }
                }
                tvGenresTitleText.setVisibility(View.VISIBLE);
                tvGenres.setVisibility(View.VISIBLE);
                tvGenres.setText(sbGenre);
            }
        }
//        tvRating.setText(movie.getCertification());
//        tvRating.setText(NumberFormatUtil.doubleFormatToString(movie.getRating(), false, 2) + "/" + movie.getVotes() + "votes");
        tvTraktRating.setText(NumberFormatUtil.doubleFormatToString(movie.getRating(), false, 2));
        tvTraktRatingCount.setText(movie.getVotes() + "votes");

        final String trailerUrl = movie.getTrailer();
        if (trailerUrl != null && trailerUrl.length() > 0) {
            showYoutube.setVisibility(View.VISIBLE);
            showYoutube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!YouTubeIntents.isYouTubeInstalled(MoCloud.context)) {
                        ToastUtil.showToast("Can't find youtube on your device.");
                        return;
                    }

//                    Intent intent = new Intent(MovieDetailActivity.this, YoutubePlayer.class);
//                    intent.putExtra("url", movie.getTrailer());
//                    startActivity(intent);

                    int subStart = trailerUrl.indexOf("=");
                    String id = trailerUrl.substring(subStart + 1);
                    //最后两个参数，是否全屏，是否播放结束后自动退出Youtube
                    Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(MoCloud.context, id, true, false);
                    startActivity(intent);
                }
            });
        }
    }

    private void initLoginView() {
        if (SPUtil.isLogin()) {
            llSync.setVisibility(View.VISIBLE);
            fbWatched.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowWatchedTime = TimeUtil.getNowUTCTime();
                    SyncData syncData = buildSyncData();
                    syncData.getMovies().get(0).setWatched_at(nowWatchedTime);
                    presenter.syncMovieWatchedState(hasWatched, syncData);
                }
            });

            fbWatchlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SyncData syncData = buildSyncData();
                    presenter.syncMovieWatchlistState(hasWatchlist,syncData);
                }
            });

            fbCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowCollectedTime = TimeUtil.getNowUTCTime();
                    SyncData syncData = buildSyncData();
                    syncData.getMovies().get(0).setCollected_at(nowCollectedTime);
//                    presenter.syncMovieCollectedState(hasCollected);
                }
            });
        }
    }
    private SyncData buildSyncData(){
        MovieSyncItem item = new MovieSyncItem();
        Ids ids = new Ids();
        ids.setTrakt(movie.getIds().getTrakt());
        item.setIds(ids);
        List<MovieSyncItem> items = new ArrayList<>();
        items.add(item);
        SyncData syncData = new SyncData();
        syncData.setMovies(items);
        return syncData;
    }

    public void updateLoginView(int viewId, RealmModel realmModel) {
        switch (viewId) {
            case R.id.fb_watched:
                hasWatched = true;
                fbWatched.setBackgroundColor(ResUtil.getColor(this, R.color.watched_color));
                fbWatched.setTextColor(ResUtil.getColor(this, R.color.white_text));
                RealmMovieWatched realmMovieWatched = (RealmMovieWatched) realmModel;
                String watchedTime = TimeUtil.formatGmtTime(realmMovieWatched.getLast_watched_at());
                fbWatched.setText("Watched at " + watchedTime);
                break;
            case R.id.fb_watchlist:
                hasWatchlist = true;
                fbWatchlist.setBackgroundColor(ResUtil.getColor(this, R.color.watchlist_color));
                fbWatchlist.setTextColor(ResUtil.getColor(this, R.color.white_text));
                RealmMovieWatchlist realmMovieWatchlist = (RealmMovieWatchlist) realmModel;
                String listedTime = TimeUtil.formatGmtTime(realmMovieWatchlist.getListed_at());
                fbWatchlist.setText("Listed on " + listedTime);
                break;
            case R.id.fb_collection:
                hasCollected = true;
                fbCollection.setBackgroundColor(ResUtil.getColor(this, R.color.collection_color));
                fbCollection.setTextColor(ResUtil.getColor(this, R.color.white_text));
                RealmMovieCollection realmMovieCollection = (RealmMovieCollection) realmModel;
                String collectedTime = TimeUtil.formatGmtTime(realmMovieCollection.getCollected_at());
                fbCollection.setText("Collected at " + collectedTime);
                break;
        }
    }

    private void initColors(Bitmap bitmap) {
        Logger.d("initColors...bitmap:" + bitmap);
        if (bitmap == null) {
            return;
        }
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int dominantColor = palette.getDominantColor(-1);
                int mutedColor = palette.getMutedColor(-1);
                int lightMutedColor = palette.getLightMutedColor(-1);
                int darkMutedColor = palette.getDarkMutedColor(-1);
                int vibrantColor = palette.getVibrantColor(-1);
                int lightVibrantColor = palette.getLightVibrantColor(-1);
                int darkVibrantColor = palette.getDarkVibrantColor(-1);

                Logger.d("PaletteTest...dominantColor:" + dominantColor + "...mutedColor:" + mutedColor + "...lightMutedColor:" + lightMutedColor + "...darkMutedColor:" + darkMutedColor + "...vibrantColor:" + vibrantColor + "...lightVibrantColor:" + lightVibrantColor + "...darkVibrantColor:" + darkVibrantColor);

                ArrayList<Palette.Swatch> swatches = ColorUtil.buildSwatchs(palette);
                if (swatches.size() == 2) {
                    mainSwatch = swatches.get(0);
                    itemSwatch = swatches.get(1);
                }

                if (enableColorful()) {
                    Logger.d("PaletteTest...实施方案");
                    if (Build.VERSION.SDK_INT >= 21) {
                        Window window = getWindow();
                        window.setStatusBarColor(ColorUtil.darkerColor(mainSwatch.getRgb(), 0.4));
                        window.setNavigationBarColor(ColorUtil.darkerColor(mainSwatch.getRgb(), 0.4));
                    }

                    toolbar.setBackgroundColor(ColorUtil.darkerColor(mainSwatch.getRgb(), 0.2));
                    llMovieContainer.setBackgroundColor(mainSwatch.getRgb());

                    int bodyTextColor = mainSwatch.getBodyTextColor();
                    itemSwatch.getTitleTextColor();
                    tvMovieTitle.setTextColor(bodyTextColor);
                    tvReleased.setTextColor(bodyTextColor);
                    tvRuntime.setTextColor(bodyTextColor);
                    tvLanguage.setTextColor(bodyTextColor);
                    tvGenres.setTextColor(bodyTextColor);
                    tvCertification.setTextColor(bodyTextColor);
                    tvOverview.setTextColor(bodyTextColor);
                    tvTomatoRating.setTextColor(bodyTextColor);
                    tvTomatoRatingCount.setTextColor(bodyTextColor);
                    tvTraktRating.setTextColor(bodyTextColor);
                    tvTraktRatingCount.setTextColor(bodyTextColor);
                    tvImdbRating.setTextColor(bodyTextColor);
                    tvImdbRatingCount.setTextColor(bodyTextColor);
                    tvOverview.setTextColor(bodyTextColor);
                    tvTagline.setTextColor(bodyTextColor);

                    int titleTextColor = mainSwatch.getTitleTextColor();
                    tvMovieTitleText.setTextColor(titleTextColor);
                    tvReleasedTitleText.setTextColor(titleTextColor);
                    tvRuntimeTitleText.setTextColor(titleTextColor);
                    tvLanguageTitleText.setTextColor(titleTextColor);
//                    tvRatingTitleText.setTextColor(titleTextColor);
                    tvGenresTitleText.setTextColor(titleTextColor);
                    tvCertificationTitleText.setTextColor(titleTextColor);
                    tvOverviewTitle.setTextColor(titleTextColor);
                    tvTaglineTitle.setTextColor(titleTextColor);
                    tvCrewTitle.setTextColor(titleTextColor);
                    tvCommentsTitle.setTextColor(titleTextColor);

                    avlCrew.setIndicatorColor(itemSwatch.getRgb());
                    avlComments.setIndicatorColor(itemSwatch.getRgb());
                }
            }
        });
    }

    public void onMovieTeamSuccess(MovieTeam movieTeam) {
        List<Staff> detailShowList = movieTeam.getDetailShowList();
        if (detailShowList == null || detailShowList.size() == 0) {
            return;
        }
        avlCrew.setVisibility(View.GONE);
        llCrew.setVisibility(View.VISIBLE);

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
                tv_crew_job.setTextColor(mainSwatch.getBodyTextColor());
                tv_crew_realname.setTextColor(mainSwatch.getBodyTextColor());
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
                tv_crew_job.setTextColor(mainSwatch.getBodyTextColor());
                tv_crew_realname.setTextColor(mainSwatch.getBodyTextColor());
                tv_crew_character.setTextColor(mainSwatch.getBodyTextColor());
            }
            llCrew.addView(actor_item);
        }
    }

    private boolean enableColorful() {
        if (mainSwatch != null && itemSwatch != null) {
            return true;
        } else {
            return false;
        }
    }

    public void onCommentsSuccess(final List<Comment> comments) {
        // TODO: 2017/1/9 Sync likes,添加回复当回复数不足3个时
        avlComments.setVisibility(View.GONE);
        commentContainer.setVisibility(View.VISIBLE);
        int showSize = comments.size() > 3 ? 3 : comments.size();
        if (comments.size() > 3) {
            tvCommentsMore.setVisibility(View.VISIBLE);
            tvCommentsMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailActivity.this, CommentsActivity.class);
                    intent.putExtra(CommentsActivity.MOVIE_OBJECT, movie);
                    if (enableColorful()) {
                        Colors mainColors = buildColors(mainSwatch);
                        Colors itemColors = buildColors(itemSwatch);
                        intent.putExtra(MAIN_COLORS, mainColors);
                        intent.putExtra(ITEM_COLORS, itemColors);
                    }

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
            tvCommentsMore.setTextColor(mainSwatch.getTitleTextColor());
            tvNoMoreComments.setTextColor(mainSwatch.getTitleTextColor());
        }

        for (int i = 0; i < showSize; i++) {
            Comment comment = comments.get(i);
            Logger.d("commentItem:" + 1);
            CardView commentItem = buildCommentItem(comment);
            Logger.d("commentItem:2" + commentItem);
            commentContainer.addView(commentItem);
        }
    }

    private CardView buildCommentItem(final Comment comment) {
        CardView commentItem = (CardView) LayoutInflater.from(this).inflate(R.layout.comment_item, commentContainer, false);
        if (enableColorful()) {
            initCommentItem(this, commentItem, comment, mainSwatch, itemSwatch, movie.getTitle(), false);
        } else {
            initCommentItem(this, commentItem, comment, null, null, movie.getTitle(), false);
        }
        return commentItem;
    }

    private void inflateCommentLayout() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        llCommentsReply = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.comment_layout, commentContainer, false);
        isSpoiler = (CheckBox) llCommentsReply.findViewById(R.id.is_spoiler);
        TextView tvSpoiler = (TextView) llCommentsReply.findViewById(R.id.tv_spoiler);
        commentBox = (EditText) llCommentsReply.findViewById(R.id.comment_box);
        ImageView ivReply = (ImageView) llCommentsReply.findViewById(R.id.iv_reply);
        movieDetailContainer.addView(llCommentsReply);
        tvNoMoreComments.setVisibility(View.GONE);
        svMovieDetail.fullScroll(View.FOCUS_DOWN);

        if (enableColorful()) {
            llCommentsReply.setBackgroundColor(ColorUtil.darkerColor(mainSwatch.getRgb(), 0.1));
            commentBox.setTextColor(mainSwatch.getBodyTextColor());
            tvSpoiler.setTextColor(mainSwatch.getTitleTextColor());
            commentBox.setHintTextColor(mainSwatch.getTitleTextColor());
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
//        tvRating.setText(NumberFormatUtil.doubleFormatToString(ratings.getRating(), false, 2) + "/" + ratings.getVotes() + "votes");
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

        String tomatoUserRating = omdbInfo.getTomatoUserRating();
        double tomatoRating = Double.valueOf(tomatoUserRating);
        if (tomatoRating < 3.5) {
            Glide.with(this).load(R.drawable.popcorn_bad).into(ivTomatoPopcornState);
        } else {
            Glide.with(this).load(R.drawable.popcorn_good).into(ivTomatoPopcornState);
        }
        tvTomatoRating.setText(tomatoUserRating);
        // TODO: 2017/2/13 西红柿的userReview应该不是rating count
        tvTomatoRatingCount.setText(omdbInfo.getTomatoUserReviews() + "votes");
        llImdbRating.setVisibility(View.VISIBLE);
        llTomatoRating.setVisibility(View.VISIBLE);
    }

    public void onAddMovieToWatchedSucceed() {
        hasWatched = true;
        fbWatched.setBackgroundColor(ResUtil.getColor(this, R.color.watched_color));
        fbWatched.setTextColor(ResUtil.getColor(this, R.color.white_text));
        String watchedTime = TimeUtil.formatGmtTime(nowWatchedTime);
        fbWatched.setText("Watched at " + watchedTime);
    }

    public void onRemoveMovieFromWatchedSucceed() {
        hasWatched = false;
        fbWatched.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        fbWatched.setTextColor(ResUtil.getColor(this, R.color.watched_color));
        fbWatched.setText("Add to watched");
    }

    public void onAddMovieToWatchlistSucceed() {
        hasWatchlist = true;
        fbWatchlist.setBackgroundColor(ResUtil.getColor(this, R.color.watchlist_color));
        fbWatchlist.setTextColor(ResUtil.getColor(this, R.color.white_text));
        fbWatchlist.setText("List on watchlist");
    }

    public void onRemoveMovieFromWatchlistSucceed() {
        hasWatchlist = false;
        fbWatchlist.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
        fbWatchlist.setTextColor(ResUtil.getColor(this, R.color.watchlist_color));
        fbWatchlist.setText("Add to watchlist");
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
        EventBus.getDefault().unregister(this);
    }
}
