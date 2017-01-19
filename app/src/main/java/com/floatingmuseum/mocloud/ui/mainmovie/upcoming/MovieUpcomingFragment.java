package com.floatingmuseum.mocloud.ui.mainmovie.upcoming;

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
import com.floatingmuseum.mocloud.ui.mainmovie.popular.TmdbMoviePopularAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public class MovieUpcomingFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_UPCOMING_FRAGMENT = "MovieUpcomingFragment";
    private MovieUpcomingPresenter presenter;
    private List<TmdbMovieDetail> upcomingList;
    private TmdbMoviePopularAdapter adapter;
    private GridLayoutManager manager;

    public static MovieUpcomingFragment newInstance() {
        MovieUpcomingFragment fragment = new MovieUpcomingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieUpcomingPresenter(this);

        initView();
        return rootView;
    }

    private void initView() {
        upcomingList = new ArrayList<>();
        adapter = new TmdbMoviePopularAdapter(upcomingList);
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
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                openMovieDetailActivity(upcomingList.get(position));
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
            upcomingList.clear();
        }
        upcomingList.addAll(data.getResults());
        adapter.notifyDataSetChanged();
    }

    protected void stopRefresh() {
        stopRefresh(srl);
    }
}
