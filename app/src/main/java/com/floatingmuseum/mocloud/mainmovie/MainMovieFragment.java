package com.floatingmuseum.mocloud.mainmovie;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.FragmentFactory;
import com.floatingmuseum.mocloud.utils.ResUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MainMovieFragment extends Fragment {

    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private MainMovieAdapter adapter;

    public static MainMovieFragment newInstance() {

        Bundle args = new Bundle();

        MainMovieFragment fragment = new MainMovieFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mainmoview, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new MainMovieAdapter(getFragmentManager());
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public static class MainMovieAdapter extends FragmentPagerAdapter{

        public MainMovieAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return FragmentFactory.create(position);
        }

        @Override
        public int getCount() {
            return ResUtil.getStringArray(R.array.tab_names).length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ResUtil.getStringArray(R.array.tab_names)[position];
        }
    }
}
