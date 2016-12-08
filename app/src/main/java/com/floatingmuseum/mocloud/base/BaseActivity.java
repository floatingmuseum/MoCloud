package com.floatingmuseum.mocloud.base;

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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.data.entity.TmdbPeopleImage;
import com.floatingmuseum.mocloud.ui.staff.StaffDetailActivity;
import com.floatingmuseum.mocloud.ui.comments.CommentsContract;

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
    protected void loadMore(LinearLayoutManager manager, BaseQuickAdapter adapter, String movieId, CommentsContract.Presenter presenter, SwipeRefreshLayout srl) {
        int lastItemPosition = manager.findLastVisibleItemPosition();
        if (lastItemPosition == (adapter.getItemCount() - 4) && !alreadyGetAllData && firstSeeLastItem) {
            firstSeeLastItem = false;
            presenter.start(movieId, false);
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
                String avatarUrl = hasImage(staff.getTmdbPeopleImage())?staff.getTmdbPeopleImage().getProfiles().get(0).getFile_path():null;
                intent.putExtra(StaffDetailActivity.STAFF_IMAGE_URL, avatarUrl);
                intent.putExtra(StaffDetailActivity.STAFF_NAME, staff.getPerson().getName());
                intent.putExtra(StaffDetailActivity.STAFF_ID, staff.getPerson().getIds().getSlug());
                startActivity(intent);
            }
        });
    }

    protected boolean hasImage(TmdbPeopleImage image) {
        if (image == null || image.getProfiles() == null || image.getProfiles().size() == 0 || image.getProfiles().get(0).getFile_path() == null) {
            return false;
        }
        return true;
    }

    protected void stopRefresh(SwipeRefreshLayout srl) {
        firstSeeLastItem = true;
        if (srl != null) {
            srl.setRefreshing(false);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
