package com.floatingmuseum.mocloud.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.Stats;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Floatingmuseum on 2016/9/14.
 */
public class UserActivity extends BaseActivity {

    @BindView(R.id.iv_userhead)
    CircleImageView ivUserhead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_is_following)
    TextView tvIsFollowing;
    @BindView(R.id.tv_following)
    TextView tvFollowing;
    @BindView(R.id.tv_followers)
    TextView tvFollowers;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.movie_stats)
    TextView movieStats;
    @BindView(R.id.tv_see_watching)
    TextView tvSeeWatching;
    @BindView(R.id.tv_see_watched)
    TextView tvSeeWatched;
    @BindView(R.id.tv_see_comments)
    TextView tvSeeComments;
    @BindView(R.id.tv_see_ratings)
    TextView tvSeeRatings;
    @BindView(R.id.tv_see_watchlist)
    TextView tvSeeWatchlist;
    @BindView(R.id.tv_see_collection)
    TextView tvSeeCollection;
    @BindView(R.id.tv_user_private)
    TextView tvUserPrivate;
    @BindView(R.id.ll_user_stats)
    LinearLayout llUserStats;

    public static final String USER_OBJECT = "user_object";
    private UserPresenter presenter;
    private User user;
    private String username;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Logger.d("username:" + SPUtil.getString(SPUtil.SP_USER_SETTINGS, "username", "-1"));
        user = getIntent().getParcelableExtra(USER_OBJECT);
        presenter = new UserPresenter(this);
        initView();
    }

    @Override
    protected void initView() {
        username = MoCloudUtil.getUsername(user);
        tvUsername.setText(username);
        actionBar.setTitle(username);
        ImageLoader.loadDontAnimate(this, MoCloudUtil.getUserAvatar(user), ivUserhead, R.drawable.default_userhead);
        boolean isUserSelf = user.getIds().getSlug().equals(SPUtil.getString(SPUtil.SP_USER_SETTINGS, "slug", ""));
        Logger.d("isUserSelf:"+isUserSelf);
        if (isUserSelf) {
            tvIsFollowing.setVisibility(View.GONE);
        }
        if (user.isPrivateX() && !isUserSelf) {
            // TODO: 2017/1/4 如果是一个private用户，可以获取到的数据有限。但是不确定一个已登录的private用户是否可以访问自己的全部资料
            llUserStats.setVisibility(View.GONE);
            tvUserPrivate.setVisibility(View.VISIBLE);
            return;
        }

        tvSeeWatching.setText("See what " + username + " watching now");
        tvSeeWatched.setText("See what " + username + " has watched");
        tvSeeComments.setText("See what " + username + " has commented");
        tvSeeRatings.setText("See what " + username + " has rated");
        tvSeeWatchlist.setText("See what " + username + "'s watchlist");
        tvSeeCollection.setText("See what " + username + "'s collections");
        presenter.start(user.getIds().getSlug());
    }

    public void onUserStatsSuccess(Stats stats) {
        tvFollowers.setText(String.valueOf(stats.getNetwork().getFollowers()));
        tvFollowing.setText(String.valueOf(stats.getNetwork().getFollowing()));
        movieStats.setText(username + " has spent " + MoCloudUtil.getTimeCost(stats.getMovies().getMinutes())
                + " for watching " + stats.getMovies().getWatched() + " movies,and has given "
                + MoCloudUtil.getAverageRating(stats.getRatings()) + " average rating, "
                + stats.getMovies().getComments() + " comments.");
        if (stats.getNetwork().getFollowers() == 0) {
            tvIsFollowing.setText("Follow");
        }
    }

    public void onUserFollowersSuccess(List<Follower> followers) {
        if (followers != null && followers.size() > 0) {
            tvIsFollowing.setText(MoCloudUtil.isFollowing(followers) ? "Following" : "Follow");
        }
    }

    public void onUserFollowingSuccess(List<Follower> followers) {
        if (followers != null && followers.size() > 0) {
        }
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
