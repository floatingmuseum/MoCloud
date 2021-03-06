package com.floatingmuseum.mocloud.ui.movie.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.floatingmuseum.mocloud.data.PaletteManager;
import com.floatingmuseum.mocloud.data.bus.CommentLikeEvent;
import com.floatingmuseum.mocloud.data.bus.EventBusManager;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieCollection;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatched;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatchlist;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.Colors;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Ids;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieSyncItem;
import com.floatingmuseum.mocloud.data.entity.MovieTeam;
import com.floatingmuseum.mocloud.data.entity.OmdbInfo;
import com.floatingmuseum.mocloud.data.entity.Ratings;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.SyncData;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    public static final String EXTRA_MOVIE = "extra_movie";
//    public static final String MOVIE_OBJECT_TRAKT= "movie_object_trakt";

    @BindView(R.id.sv_movie_detail)
    ScrollView svMovieDetail;
    @BindView(R.id.ll_movie_detail)
    LinearLayout movieDetailContainer;
    @BindView(R.id.iv_tomato_state)
    ImageView ivTomatoState;
    @BindView(R.id.tv_tomato_rating)
    TextView tvTomatoRating;
    @BindView(R.id.ll_tomato_rating)
    LinearLayout llTomatoRating;
    @BindView(R.id.iv_trakt_rating)
    ImageView ivTraktRating;
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
    @BindView(R.id.ll_meta_score)
    LinearLayout llMetaScore;
    @BindView(R.id.tv_meta_score)
    TextView tvMetaScore;
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
    @BindView(R.id.tv_no_crew)
    TextView tvNoCrew;

    @BindView(R.id.ll_movie_header_with_backdrop)
    LinearLayout llMovieHeaderWithBackdrop;
    @BindView(R.id.backdrop)
    RatioImageView backdrop;
    @BindView(R.id.tv_title_text_backdrop)
    TextView tvTitleTextBackdrop;
    @BindView(R.id.tv_title_backdrop)
    TextView tvTitleBackdrop;
    @BindView(R.id.tv_released_title_text_backdrop)
    TextView tvReleasedTitleTextBackdrop;
    @BindView(R.id.tv_released_backdrop)
    TextView tvReleasedBackdrop;
    @BindView(R.id.tv_runtime_title_text_backdrop)
    TextView tvRuntimeTitleTextBackdrop;
    @BindView(R.id.tv_runtime_backdrop)
    TextView tvRuntimeBackdrop;
    @BindView(R.id.tv_language_title_text_backdrop)
    TextView tvLanguageTitleTextBackdrop;
    @BindView(R.id.tv_language_backdrop)
    TextView tvLanguageBackdrop;
    @BindView(R.id.tv_certification_title_text_backdrop)
    TextView tvCertificationTitleTextBackdrop;
    @BindView(R.id.tv_certification_backdrop)
    TextView tvCertificationBackdrop;
    @BindView(R.id.tv_genres_title_text_backdrop)
    TextView tvGenresTitleTextBackdrop;
    @BindView(R.id.tv_genres_backdrop)
    TextView tvGenresBackdrop;

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
    private String nowListedTime;
    private List<Comment> comments = new ArrayList<>();
    private ArtImage artImage;


    @Override
    protected int currentLayoutId() {
        return R.layout.activity_moviedetail;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        artImage = movie.getImage();
        EventBusManager.register(this);
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

    private boolean isHaveBackdrop() {
        if (artImage == null) {
            return false;
        } else {
            return artImage.getLocalBackdropUri() != null || !TextUtils.isEmpty(artImage.getRemoteBackdropUrl());
        }
    }

    @Override
    protected void initView() {
        initLoginView();
        avlComments.smoothToShow();
        avlCrew.smoothToShow();
//        ImageLoader.load(this, movie.getImage().getBitmap(), ivPoster, R.drawable.default_movie_poster);
//        artImage = movie.getImage();
        initColors(PaletteManager.getInstance().getBitmap(movie.getIds().getTmdb()));

        actionBar.setTitle(movie.getTitle());
        initHeaderInfo();
        String overView = movie.getOverview();

        tvOverview.setText(TextUtils.isEmpty(overView) ? ResUtil.getString(R.string.empty_data) : overView);
        String tagline = movie.getTagline();
        if (StringUtil.hasData(tagline)) {
            tvTagline.setText("\"" + tagline + "\"");
        } else {
            tvTaglineTitle.setVisibility(View.GONE);
            tvTagline.setVisibility(View.GONE);
        }


//        if (movie.getCertification() != null && movie.getCertification().length() > 0) {
//            tvCertificationTitleText.setVisibility(View.VISIBLE);
//            tvCertification.setVisibility(View.VISIBLE);
//            tvCertification.setText(movie.getCertification());
//        } else {
//            List<String> genres = movie.getGenres();
//            if (genres != null && genres.size() > 0) {
//                StringBuffer sbGenre = new StringBuffer();
//                for (int i = 0; i < genres.size(); i++) {
//                    if (i == (genres.size() - 1)) {
//                        sbGenre.append(genres.get(i));
//                    } else {
//                        sbGenre.append(genres.get(i)).append(",");
//                    }
//                }
//                tvGenresTitleText.setVisibility(View.VISIBLE);
//                tvGenres.setVisibility(View.VISIBLE);
//                tvGenres.setText(sbGenre);
//            }
//        }


//        tvRating.setText(movie.getCertification());
//        tvRating.setText(NumberFormatUtil.doubleFormatToString(movie.getRating(), false, 2) + "/" + movie.getVotes() + "votes");
        tvTraktRating.setText(NumberFormatUtil.doubleFormatToString(movie.getRating(), false, 2));
        tvTraktRatingCount.setText(movie.getVotes() + "votes");

        llTraktRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInBrowser("https://trakt.tv/movies/" + movie.getIds().getSlug());
            }
        });

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

    private void initHeaderInfo() {
        if (isHaveBackdrop()) {
            llMovieHeaderWithBackdrop.setVisibility(View.VISIBLE);
            llMovieHeader.setVisibility(View.GONE);
            ImageLoader.loadArtImage(this, artImage, backdrop, ImageCacheManager.TYPE_BACKDROP);
            tvTitleBackdrop.setText(movie.getTitle());
            tvReleasedBackdrop.setText(movie.getReleased());
            tvRuntimeBackdrop.setText(movie.getRuntime() + " mins");
            tvLanguageBackdrop.setText(movie.getLanguage());

            if (movie.getCertification() != null && movie.getCertification().length() > 0) {
                tvCertificationTitleTextBackdrop.setVisibility(View.VISIBLE);
                tvCertificationBackdrop.setVisibility(View.VISIBLE);
                tvCertificationBackdrop.setText(movie.getCertification());
            } else {
                List<String> genres = movie.getGenres();
                if (genres != null && genres.size() > 0) {
                    StringBuffer sbGenre = new StringBuffer();
                    for (int i = 0; i < genres.size(); i++) {
                        if (i == (genres.size() - 1)) {
                            sbGenre.append(genres.get(i));
                        } else {
                            sbGenre.append(genres.get(i)).append(",");
                        }
                    }
                    tvGenresTitleTextBackdrop.setVisibility(View.VISIBLE);
                    tvGenresBackdrop.setVisibility(View.VISIBLE);
                    tvGenresBackdrop.setText(sbGenre);
                }
            }
        } else {
            llMovieHeader.setVisibility(View.VISIBLE);
            llMovieHeaderWithBackdrop.setVisibility(View.GONE);
            ImageLoader.loadArtImage(this, artImage, ivPoster, ImageCacheManager.TYPE_POSTER);
            tvMovieTitle.setText(movie.getTitle());
            tvReleased.setText(movie.getReleased());
            tvRuntime.setText(movie.getRuntime() + " mins");
            tvLanguage.setText(movie.getLanguage());

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
                            sbGenre.append(genres.get(i)).append(",");
                        }
                    }
                    tvGenresTitleText.setVisibility(View.VISIBLE);
                    tvGenres.setVisibility(View.VISIBLE);
                    tvGenres.setText(sbGenre);
                }
            }
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
                    nowListedTime = TimeUtil.getNowUTCTime();
                    syncData.getMovies().get(0).setListed_at(nowListedTime);
                    presenter.syncMovieWatchlistState(hasWatchlist, syncData);
                }
            });

            fbCollection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nowCollectedTime = TimeUtil.getNowUTCTime();
                    SyncData syncData = buildSyncData();
                    syncData.getMovies().get(0).setCollected_at(nowCollectedTime);
                    presenter.syncMovieCollectedState(hasCollected, syncData);
                }
            });
        }
    }

    private SyncData buildSyncData() {
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

    private void openInBrowser(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.parse(url);
        intent.setData(uri);
        startActivity(intent);
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
        //默认色值,默认色值为异形电影海报色值.
        mainSwatch = new Palette.Swatch(Color.parseColor("#ff687890"), 152);
        itemSwatch = new Palette.Swatch(Color.parseColor("#ff203038"), 1220);
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
                if (swatches.size() == 2) {//如果获取海报色值成功,则使用更合适的当前海报色值
                    mainSwatch = swatches.get(0);
                    itemSwatch = swatches.get(1);
                    Logger.d("MainSwatch:" + mainSwatch.toString() + "...itemSwatch:" + itemSwatch.toString());
//                    Color.parseColor("#ff687890");
//                    Color.parseColor("#ff203038");
//                    Palette.Swatch customMain = new Palette.Swatch(Color.parseColor("#ff687890"), 152);
//                    Palette.Swatch customItem = new Palette.Swatch(Color.parseColor("#ff203038"), 1220);
//                    Logger.d("MainSwatch:" + customMain.toString() + "...itemSwatch:" + customItem.toString());
                }

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

                if (isHaveBackdrop()) {
                    tvTitleBackdrop.setTextColor(bodyTextColor);
                    tvReleasedBackdrop.setTextColor(bodyTextColor);
                    tvRuntimeBackdrop.setTextColor(bodyTextColor);
                    tvLanguageBackdrop.setTextColor(bodyTextColor);
                    tvGenresBackdrop.setTextColor(bodyTextColor);
                    tvCertificationBackdrop.setTextColor(bodyTextColor);
                } else {
                    tvMovieTitle.setTextColor(bodyTextColor);
                    tvReleased.setTextColor(bodyTextColor);
                    tvRuntime.setTextColor(bodyTextColor);
                    tvLanguage.setTextColor(bodyTextColor);
                    tvGenres.setTextColor(bodyTextColor);
                    tvCertification.setTextColor(bodyTextColor);
                }

                tvOverview.setTextColor(bodyTextColor);
                tvTomatoRating.setTextColor(bodyTextColor);
                tvTraktRating.setTextColor(bodyTextColor);
                tvTraktRatingCount.setTextColor(bodyTextColor);
                tvImdbRating.setTextColor(bodyTextColor);
                tvImdbRatingCount.setTextColor(bodyTextColor);
                tvOverview.setTextColor(bodyTextColor);
                tvTagline.setTextColor(bodyTextColor);
                tvNoCrew.setTextColor(bodyTextColor);

                int titleTextColor = mainSwatch.getTitleTextColor();
                if (isHaveBackdrop()) {
                    tvTitleTextBackdrop.setTextColor(titleTextColor);
                    tvReleasedTitleTextBackdrop.setTextColor(titleTextColor);
                    tvRuntimeTitleTextBackdrop.setTextColor(titleTextColor);
                    tvLanguageTitleTextBackdrop.setTextColor(titleTextColor);
//                    tvRatingTitleText.setTextColor(titleTextColor);
                    tvGenresTitleTextBackdrop.setTextColor(titleTextColor);
                    tvCertificationTitleTextBackdrop.setTextColor(titleTextColor);
                } else {
                    tvMovieTitleText.setTextColor(titleTextColor);
                    tvReleasedTitleText.setTextColor(titleTextColor);
                    tvRuntimeTitleText.setTextColor(titleTextColor);
                    tvLanguageTitleText.setTextColor(titleTextColor);
//                    tvRatingTitleText.setTextColor(titleTextColor);
                    tvGenresTitleText.setTextColor(titleTextColor);
                    tvCertificationTitleText.setTextColor(titleTextColor);
                }
                tvOverviewTitle.setTextColor(titleTextColor);
                tvTaglineTitle.setTextColor(titleTextColor);
                tvCrewTitle.setTextColor(titleTextColor);
                tvCommentsTitle.setTextColor(titleTextColor);
                avlCrew.setIndicatorColor(itemSwatch.getRgb());
                avlComments.setIndicatorColor(itemSwatch.getRgb());

            }
        });
    }

    public void onMovieTeamSuccess(MovieTeam movieTeam) {
        List<Staff> detailShowList = movieTeam.getDetailShowList();
        if (detailShowList == null || detailShowList.size() == 0) {
            avlCrew.setVisibility(View.GONE);
            tvNoCrew.setVisibility(View.VISIBLE);
            tvNoCrew.setText(R.string.no_data);
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
//            director_item.findViewById(R.id.tv_crew_character).setVisibility(View.GONE);
            ImageLoader.loadFromTmdbPersonImage(this, director.getTmdbPersonImage(), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText(director.getJob());
            tv_crew_realname.setText(director.getPerson().getName());

            tv_crew_job.setTextColor(mainSwatch.getBodyTextColor());
            tv_crew_realname.setTextColor(mainSwatch.getBodyTextColor());

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
//            TextView tv_crew_character = (TextView) actor_item.findViewById(R.id.tv_crew_character);
            ImageLoader.loadFromTmdbPersonImage(this, cast.getTmdbPersonImage(), iv_staff_headshot, R.drawable.default_movie_poster);
            tv_crew_job.setText("Actor");
            tv_crew_realname.setText(cast.getPerson().getName());
//            tv_crew_character.setText(cast.getCharacter());

            tv_crew_job.setTextColor(mainSwatch.getBodyTextColor());
            tv_crew_realname.setTextColor(mainSwatch.getBodyTextColor());
//                tv_crew_character.setTextColor(mainSwatch.getBodyTextColor());

            llCrew.addView(actor_item);
        }
    }

    private boolean enableColorful() {
        return mainSwatch != null && itemSwatch != null;
    }

    public void onCommentsSuccess(final List<Comment> commentsResult) {
        this.comments.addAll(commentsResult);
        avlComments.setVisibility(View.GONE);
        commentContainer.setVisibility(View.VISIBLE);
        int showSize = comments.size() > 3 ? 3 : comments.size();
        if (comments.size() > 3) {
            tvCommentsMore.setVisibility(View.VISIBLE);
            tvCommentsMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailActivity.this, CommentsActivity.class);
                    intent.putExtra(CommentsActivity.EXTRA_MOVIE, movie);

                    Colors mainColors = buildColors(mainSwatch);
                    Colors itemColors = buildColors(itemSwatch);
                    intent.putExtra(MAIN_COLORS, mainColors);
                    intent.putExtra(ITEM_COLORS, itemColors);

                    startActivity(intent);
                }
            });
        } else {
            tvNoMoreComments.setVisibility(View.VISIBLE);
            tvNoMoreComments.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.d("tv_no_more_comments...点击");
                    inflateCommentReplyLayout();
                }
            });
        }


        tvCommentsMore.setTextColor(mainSwatch.getTitleTextColor());
        tvNoMoreComments.setTextColor(mainSwatch.getTitleTextColor());


        for (int i = 0; i < showSize; i++) {
            Comment comment = comments.get(i);
            Logger.d("commentItem:" + 1);
            CardView commentItem = buildCommentItem(comment);
            Logger.d("commentItem:2" + commentItem);
            commentContainer.addView(commentItem);
        }
    }

    private CardView buildCommentItem(final Comment comment) {
        // TODO: 2017/3/28 want switch likes and replies position
        CardView commentItem = (CardView) LayoutInflater.from(this).inflate(R.layout.comment_item, commentContainer, false);
        initCommentItem(this, commentItem, comment, mainSwatch, itemSwatch, movie.getTitle(), false);
        return commentItem;
    }

    @Override
    protected void syncCommentLike(boolean isLike, Comment comment) {
        presenter.syncCommentLike(isLike, comment, presenter);
    }

    public void onAddCommentToLikesSuccess(long commentId) {
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            if (commentId == comment.getId()) {
                updateCommentLikesView(true, (CardView) commentContainer.getChildAt(i), comment);
            }
        }
    }


    public void onRemoveCommentFromLikesSuccess(long commentId) {
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            if (commentId == comment.getId()) {
                updateCommentLikesView(false, (CardView) commentContainer.getChildAt(i), comment);
            }
        }
    }


    private void inflateCommentReplyLayout() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        // TODO: 2017/4/10 超过4行时存在滑动冲突
        llCommentsReply = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.comment_layout, llMovieContainer, false);
        isSpoiler = (CheckBox) llCommentsReply.findViewById(R.id.is_spoiler);
        TextView tvSpoiler = (TextView) llCommentsReply.findViewById(R.id.tv_spoiler);
        commentBox = (EditText) llCommentsReply.findViewById(R.id.comment_box);

        // TODO: 2017/4/9 添加点击阴影效果不成功 
        ImageView ivReply = (ImageView) llCommentsReply.findViewById(R.id.iv_reply);
//        Drawable icon1 = OneDrawable.createBgDrawableWithDarkMode(this, R.drawable.ic_send_black_48dp);
//        ivReply.setBackgroundDrawable(icon1);
//        ivReply.setBackground(icon1);

        movieDetailContainer.addView(llCommentsReply);
        tvNoMoreComments.setVisibility(View.GONE);
        svMovieDetail.fullScroll(View.FOCUS_DOWN);

        llCommentsReply.setBackgroundColor(ColorUtil.darkerColor(mainSwatch.getRgb(), 0.1));
        commentBox.setTextColor(mainSwatch.getBodyTextColor());
        tvSpoiler.setTextColor(mainSwatch.getTitleTextColor());
        commentBox.setHintTextColor(mainSwatch.getTitleTextColor());

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
        comments.add(comment);
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
        // TODO: 2017/4/13 部分投票人数超多的影片可能造成trakt和imdb的投票人数显示重叠，暂时没出现，理论上猜测 
        //imdb score
        String imdbRating = omdbInfo.getImdbRating();
        tvImdbRating.setText(imdbRating == null ? "N/A" : imdbRating);
        String imdbVotes = omdbInfo.getImdbVotes();
        imdbVotes = imdbVotes == null ? "N/A" : omdbInfo.getImdbVotes().replace(",", "") + "votes";
        tvImdbRatingCount.setText(imdbVotes);
        llImdbRating.setVisibility(View.VISIBLE);
        llImdbRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInBrowser("http://www.imdb.com/title/" + movie.getIds().getImdb());
            }
        });

        //metacritic score
        String metaScore = omdbInfo.getMetascore();
        if (metaScore != null && !"N/A".equals(metaScore)) {
            int height = ivTraktRating.getHeight();
            int metaInt = Integer.valueOf(metaScore);
            if (metaInt >= 75) {
                tvMetaScore.setBackgroundColor(Color.parseColor("#66CC33"));
            } else if (metaInt >= 50) {
                tvMetaScore.setBackgroundColor(Color.parseColor("#FFCC33"));
            } else {
                tvMetaScore.setBackgroundColor(Color.parseColor("#FF0000"));
            }
            tvMetaScore.setText(metaScore);
            tvMetaScore.setWidth(height);
            llMetaScore.setVisibility(View.VISIBLE);
        }

        //rotten tomato score
        List<OmdbInfo.RatingsBean> ratingsBeans = omdbInfo.getRatings();
        if (ratingsBeans != null) {
            for (OmdbInfo.RatingsBean ratingsBean : ratingsBeans) {
                if ("Rotten Tomatoes".equals(ratingsBean.getSource())) {
                    String tomatoRating = ratingsBean.getValue();
                    String value = tomatoRating.substring(0, tomatoRating.indexOf("%"));
                    Logger.d("Value:" + value);
                    int valueInt = Integer.valueOf(value);
                    if (valueInt >= 75) {
                        Glide.with(this).load(R.drawable.tomato_certified).into(ivTomatoState);
                    } else if (valueInt >= 60) {
                        Glide.with(this).load(R.drawable.tomato_fresh).into(ivTomatoState);
                    } else {
                        Glide.with(this).load(R.drawable.tomato_rot).into(ivTomatoState);
                    }
                    tvTomatoRating.setText(tomatoRating);
                    llTomatoRating.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public void onAddMovieToWatchedSuccess() {
        hasWatched = true;
        String watchedTime = TimeUtil.formatGmtTime(nowWatchedTime);
        changeFancyButton(fbWatched, R.color.watched_color, R.color.white_text, "Watched at " + watchedTime);
        //当一个处于想看状态的电影，被点击看过之后，要移除其想看的状态
        changeFancyButton(fbWatchlist, android.R.color.transparent, R.color.watchlist_color, "Add to watchlist");
    }

    public void onRemoveMovieFromWatchedSuccess() {
        hasWatched = false;
        changeFancyButton(fbWatched, android.R.color.transparent, R.color.watched_color, "Add to watched");
    }

    public void onAddMovieToWatchlistSuccess() {
        hasWatchlist = true;
        String listedTime = TimeUtil.formatGmtTime(nowListedTime);
        changeFancyButton(fbWatchlist, R.color.watchlist_color, R.color.white_text, "Listed at " + listedTime);
    }

    public void onRemoveMovieFromWatchlistSuccess() {
        hasWatchlist = false;
        changeFancyButton(fbWatchlist, android.R.color.transparent, R.color.watchlist_color, "Add to watchlist");
    }

    public void onAddMovieToCollectionSuccess() {
        hasCollected = true;
        String collectedTime = TimeUtil.formatGmtTime(nowCollectedTime);
        changeFancyButton(fbCollection, R.color.collection_color, R.color.white_text, "Collected at " + collectedTime);
    }

    public void onRemoveMovieFromCollectionSuccess() {
        hasCollected = false;
        changeFancyButton(fbCollection, android.R.color.transparent, R.color.collection_color, "Add to collection");
    }

    private void changeFancyButton(FancyButton fb, int backgroundColor, int textColor, String text) {
        fb.setBackgroundColor(ResUtil.getColor(this, backgroundColor));
        fb.setTextColor(ResUtil.getColor(this, textColor));
        fb.setText(text);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCommentLikeEvent(CommentLikeEvent event) {
        for (int i = 0; i < comments.size(); i++) {
            Comment comment = comments.get(i);
            if (event.commentId == comment.getId()) {
                updateCommentLikesView(event.isLike, (CardView) commentContainer.getChildAt(i), comment);
            }
        }
    }

    @Override
    protected void onError(Exception e) {
        // TODO: 2017/4/25 需要展示crew获取数据失败和comment获取数据失败时的点击retry
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
        EventBus.getDefault().unregister(this);
    }
}
