package com.floatingmuseum.mocloud.ui.mainmovie.popular;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public class MoviePopularFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_POPILAR_FRAGMENT = "MoviePopularFragment";
    private MoviePopularPresenter presenter;
    private List<TmdbMovieDetail> popularList;
    private MoviePopularAdapter adapter;
    private GridLayoutManager manager;

    public static MoviePopularFragment newInstance() {
        MoviePopularFragment fragment = new MoviePopularFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MoviePopularPresenter(this);

        initView();
        return rootView;
    }

    protected void initView() {
        popularList = new ArrayList<>();
        adapter = new MoviePopularAdapter(popularList);
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
//                openMovieDetailActivity(popularList.get(i), true);
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
            popularList.clear();
        }
        popularList.addAll(data.getResults());
        adapter.notifyDataSetChanged();
    }

    protected void stopRefresh() {
        stopRefresh(srl);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
