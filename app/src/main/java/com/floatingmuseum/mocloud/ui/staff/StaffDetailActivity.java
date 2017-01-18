package com.floatingmuseum.mocloud.ui.staff;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.entity.StaffImages;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.RxUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.functions.Func1;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class StaffDetailActivity extends BaseActivity {


    public static final String STAFF_IMAGE = "staff_image";
    public static final String STAFF_NAME = "staff_name";
    public static final String STAFF_ID = "staff_id";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.staff_avatar)
//    RatioImageView staffAvatar;
    @BindView(R.id.staff_avatar)
    ImageView staffAvatar;
    //    @BindView(R.id.rv_pic_wall)
//    RecyclerView rvPicWall;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.staff_detail_tab)
    TabLayout staffDetailTab;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.staff_viewpager)
    ViewPager staffViewpager;

    private String staffName;
    private int staffId;
    private String staffImageUrl;
//    private StaffDetailPresenter presenter;
//    private StaffImagesAdapter adapter;
//    private List<StaffImages.Profiles> imagesList;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_staff_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        staffName = getIntent().getStringExtra(STAFF_NAME);
        staffImageUrl = getIntent().getStringExtra(STAFF_IMAGE);
        staffId = getIntent().getIntExtra(STAFF_ID, -1);
        initView();
//        presenter = new StaffDetailPresenter(this);
//        presenter.getStaffImages(staffId);
    }

    @Override
    protected void initView() {
        actionBar.setTitle(staffName);
        ImageLoader.load(this, StringUtil.buildPeopleHeadshotUrl(staffImageUrl), staffAvatar, R.drawable.default_movie_poster);
        StaffDetailPagerAdapter pagerAdapter = new StaffDetailPagerAdapter(getSupportFragmentManager(), staffId);
        staffViewpager.setAdapter(pagerAdapter);
        staffDetailTab.setupWithViewPager(staffViewpager);

        staffAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("staffAvatar...onClick");
            }
        });
    }

    public void onBaseDataSuccess(StaffImages staffImages) {
//        imagesList.addAll(staffImages.getProfiles());
//        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
