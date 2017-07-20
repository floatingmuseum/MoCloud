package com.floatingmuseum.mocloud.ui.tv;

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

public class TVFragment extends Fragment {

    public static final String TAG = TVFragment.class.getSimpleName();
    @BindView(R.id.tab_tv)
    TabLayout tabTv;
    @BindView(R.id.viewpager_tv)
    ViewPager vpTv;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("生命周期", "TVFragment...onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("生命周期", "TVFragment...onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_tv, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("生命周期", "TVFragment...onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView() {
        toolbar.setTitle("MoCloud");
        vpTv.setAdapter(new TVPagerAdapter(getFragmentManager()));
        tabTv.setupWithViewPager(vpTv);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("生命周期", "TVFragment...onDestroy");
    }
}
