package com.floatingmuseum.mocloud.mainmovie.Trending;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.model.entity.Image;
import com.floatingmuseum.mocloud.model.entity.Movie;
import com.floatingmuseum.mocloud.model.entity.Trending;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieTrendingFragment extends BaseFragment implements TrendingContract.View {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_TRENDING_FRAGMENT = "MovieTrendingFragment";
    private List<Trending> trendingList;
    private List<Image> imageList;
    private TrendingAdapter adapter;
    private TrendingContract.Presenter mTrendingPresenter;

    private TrendingAdapter trendingAdapter;

    public static MovieTrendingFragment newInstance() {
        MovieTrendingFragment fragment = new MovieTrendingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);
        new TrendingPresenter(this);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        trendingList = new ArrayList<>();
        imageList = new ArrayList<>();
        adapter =  new TrendingAdapter(trendingList,imageList,context);
        GridLayoutManager manager = new GridLayoutManager(context,3);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
    }

    @Override
    public void setPresenter(TrendingContract.Presenter presenter) {
        mTrendingPresenter = presenter;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        srl.setRefreshing(true);
        mTrendingPresenter.start();
    }

    @Override
    public void refreshData(List<Trending> newData,List<Image> images) {
        trendingList.clear();
        trendingList.addAll(newData);
        imageList.clear();
        imageList.addAll(images);
        adapter.notifyDataSetChanged();
        srl.setRefreshing(false);
    }

    @Override
    public TrendingAdapter getTrendingAdapter() {
        return trendingAdapter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
