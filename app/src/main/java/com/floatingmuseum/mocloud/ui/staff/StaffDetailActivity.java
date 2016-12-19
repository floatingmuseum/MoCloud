package com.floatingmuseum.mocloud.ui.staff;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.MovieDetail;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.TmdbPeopleImage;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;
import com.floatingmuseum.mocloud.ui.mainmovie.detail.MovieDetailActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class StaffDetailActivity extends BaseActivity {


    public static final String STAFF_IMAGE = "staff_image";
    public static final String STAFF_NAME = "staff_name";
    public static final String STAFF_ID = "staff_id";

    @BindView(R.id.staff_rv)
    RecyclerView staff_rv;
    private GridLayoutManager manager;
    private List<TmdbStaff.Credits.Cast> playList;
    private String staffName;
//    private String avatarUrl;
    private String staffId;
    private RatioImageView avatar;
    private TextView birthPlace;
    private TextView birthDay;
    private TextView deathDay;
    private TextView deathDayTitle;
    private TextView homepage;
    private TextView biography;
    private StaffDetailAdapter adapter;
    private TmdbPeopleImage tmdbPeopleImage;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_staff_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        staffName = getIntent().getStringExtra(STAFF_NAME);
//        avatarUrl = getIntent().getStringExtra(STAFF_IMAGE);
        tmdbPeopleImage = getIntent().getParcelableExtra(STAFF_IMAGE);
        staffId = getIntent().getStringExtra(STAFF_ID);

        StaffDetailPresenter presenter = new StaffDetailPresenter(this, Repository.getInstance());
//        Logger.d("id:" + staffId + "...name:" + staffName + "...url:" + avatarUrl);
        initView();
        presenter.getData(staffId);
    }

    @Override
    protected void initView() {
        actionBar.setTitle(staffName);

        playList = new ArrayList();
        adapter = new StaffDetailAdapter(playList);
        staff_rv.setHasFixedSize(true);
        LinearLayout staffHeaderView = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.staff_detail_headview, null);
        avatar = (RatioImageView) staffHeaderView.findViewById(R.id.avatar);
        birthPlace = (TextView) staffHeaderView.findViewById(R.id.tv_place_of_birth);
        birthDay = (TextView) staffHeaderView.findViewById(R.id.tv_birthday);
        deathDay = (TextView) staffHeaderView.findViewById(R.id.tv_deathday);
        deathDayTitle = (TextView) staffHeaderView.findViewById(R.id.tv_deathday_title);
        homepage = (TextView) staffHeaderView.findViewById(R.id.tv_website);
        biography = (TextView) staffHeaderView.findViewById(R.id.tv_biography);
        adapter.addHeaderView(staffHeaderView);
        manager = new GridLayoutManager(this, 3);
        staff_rv.setLayoutManager(manager);
        staff_rv.setAdapter(adapter);
        staff_rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                TmdbStaff.Credits.Cast cast = playList.get(i);
//                Intent intent = new Intent(StaffDetailActivity.this, MovieDetailActivity.class);
//                intent.putExtra(MovieDetailActivity.CAST_OBJECT,cast);
//                startActivity(intent);
            }
        });
        loadPeopleImage(tmdbPeopleImage,avatar);
//        ImageLoader.load(this, StringUtil.buildPeopleHeadshotUrl(avatarUrl), avatar, R.drawable.default_movie_poster);
    }

    public void onBaseDataSuccess(Person person) {
        birthPlace.setText(person.getBirthplace());
        birthDay.setText(person.getBirthday());
        if (homepage != null) {
            homepage.setText(person.getHomepage());
        } else {
            homepage.setVisibility(View.GONE);
        }

        if (person.getDeath() != null && person.getDeath().length() > 0) {
            deathDayTitle.setVisibility(View.VISIBLE);
            deathDay.setVisibility(View.VISIBLE);
            deathDay.setText(person.getDeath());
        }
        biography.setText(person.getBiography());

//        playList.addAll(staff.getCredits().getCast());
//        adapter.notifyDataSetChanged();
    }
}
