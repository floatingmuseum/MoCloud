package com.floatingmuseum.mocloud.ui.mainmovie.toprated;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.ui.mainmovie.BaseFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.nowplaying.MovieNowPlayingPresenter;
import com.floatingmuseum.mocloud.ui.mainmovie.popular.MoviePopularAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public class MovieTopRatedFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_TOP_RATED_FRAGMENT = "MovieTopRatedFragment";
    private MovieTopRatedPresenter presenter;
    private List<TmdbMovieDetail> topRatedList;
    private MoviePopularAdapter adapter;
    private GridLayoutManager manager;

    public static MovieTopRatedFragment newInstance() {
        MovieTopRatedFragment fragment = new MovieTopRatedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieTopRatedPresenter(this);

        initView();
        return rootView;
    }

    private void initView() {
        topRatedList = new ArrayList<>();
        adapter = new MoviePopularAdapter(topRatedList);
        rv.setHasFixedSize(true);
        manager = new GridLayoutManager(context, 2);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start(true);
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(manager, adapter, presenter, srl);
            }
        });

        rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                openMovieDetailActivity(topRatedList.get(i));
            }
        });
        requestBaseData();
    }

    @Override
    protected void requestBaseData() {
        srl.setRefreshing(true);
        presenter.start(true);
    }

    public void refreshData(TmdbMovieDataList data, boolean shouldClean) {
        if (data.getPage() == data.getTotal_pages()) {
            alreadyGetAllData = true;
        }

        if (shouldClean) {
            topRatedList.clear();
        }
        topRatedList.addAll(data.getResults());
        adapter.notifyDataSetChanged();
    }

    protected void stopRefresh() {
        stopRefresh(srl);
    }
}
