package com.floatingmuseum.mocloud.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbPersonImage;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.ui.comments.CommentsPresenter;
import com.floatingmuseum.mocloud.ui.staff.StaffDetailActivity;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;

import java.io.File;

/**
 * Created by Floatingmuseum on 2016/8/31.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected boolean alreadyGetAllData = false;
    protected boolean firstSeeLastItem = true;
    protected boolean notFirstLoadData = false;
    protected boolean shouldClean = true;
    protected ActionBar actionBar;

    abstract protected int currentLayoutId();

    abstract protected void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(currentLayoutId());
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if (canGoBack()) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setOnClickListener(this);
    }

    protected boolean canGoBack() {
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 加载更多
     * 条件：
     */
    protected void loadMoreComments(LinearLayoutManager manager, BaseQuickAdapter adapter, String sortCondition, String movieId, CommentsPresenter presenter, SwipeRefreshLayout srl) {
        int lastItemPosition = manager.findLastVisibleItemPosition();
        if (lastItemPosition == (adapter.getItemCount() - 4) && !alreadyGetAllData && firstSeeLastItem) {
            firstSeeLastItem = false;
            presenter.start(sortCondition, movieId, false);
            shouldClean = false;
            if (notFirstLoadData) {
                srl.setRefreshing(true);
            } else {
                notFirstLoadData = true;
            }
        }
    }

    protected void setStaffClickListener(View view, final Staff staff) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaseActivity.this, StaffDetailActivity.class);
//                intent.putExtra(StaffDetailActivity.STAFF_IMAGE, staff.getProfile_path());
//                intent.putExtra(StaffDetailActivity.STAFF_NAME, staff.getName());
//                intent.putExtra(StaffDetailActivity.STAFF_ID, staff.getId());
                startActivity(intent);
            }
        });
    }

    protected void loadPeopleImage(TmdbPersonImage tmdbPeopleImage, ImageView headView) {
        if (tmdbPeopleImage != null) {
            if (tmdbPeopleImage.isHasCache()) {
                File file = tmdbPeopleImage.getCacheFile();
                ImageLoader.load(this, file, headView, R.drawable.default_movie_poster);
                return;
            } else if (tmdbPeopleImage.isHasAvatar()) {
                ImageLoader.load(this, StringUtil.buildPeopleHeadshotUrl(tmdbPeopleImage.getProfiles().get(0).getFile_path()), headView, R.drawable.default_movie_poster);
                return;
            }
        }
        ImageLoader.loadDefault(this, headView);
    }

    protected void stopRefresh(SwipeRefreshLayout srl) {
        firstSeeLastItem = true;
        if (srl != null) {
            srl.setRefreshing(false);
        }
    }

    protected void openUserActivity(Context context, User user) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(UserActivity.USER_OBJECT, user);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回键
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected abstract void onError(Exception e);
}
