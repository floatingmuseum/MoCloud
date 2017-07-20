package com.floatingmuseum.mocloud.ui.movie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.floatingmuseum.mocloud.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Floatingmuseum on 2017/7/19.
 */

public class MovieFragment extends Fragment {

    public static final String TAG = MovieFragment.class.getSimpleName();
    @BindView(R.id.tab_movie)
    TabLayout tabMovie;
    @BindView(R.id.viewpager_movie)
    ViewPager vpMovie;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("生命周期", "MovieFragment...onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("生命周期", "MovieFragment...onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        toolbar.setTitle("MoCloud");
        vpMovie.setAdapter(new MoviePagerAdapter(getFragmentManager()));
        tabMovie.setupWithViewPager(vpMovie);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("生命周期", "MovieFragment...onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("生命周期", "MovieFragment...onDestroy");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
