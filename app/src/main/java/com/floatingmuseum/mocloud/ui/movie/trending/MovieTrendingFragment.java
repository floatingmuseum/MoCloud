package com.floatingmuseum.mocloud.ui.movie.trending;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieTrendingFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.tv_loaded_failed)
    TextView tvLoadedFailed;

    public final static String MOVIE_TRENDING_FRAGMENT = "MovieTrendingFragment";

    private List<BaseMovie> trendingList;
    private TrendingAdapter adapter;

    private MovieTrendingPresenter presenter;
    private GridLayoutManager manager;

    public static MovieTrendingFragment newInstance() {
        return new MovieTrendingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieTrendingPresenter(this);

        initView();
        return rootView;
    }

    protected void initView() {
        trendingList = new ArrayList<>();
        adapter = new TrendingAdapter(trendingList);
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
                openMovieDetailActivity(trendingList.get(position).getMovie());
            }
        });

        tvLoadedFailed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLoadedFailed.setVisibility(View.GONE);
                srl.setVisibility(View.VISIBLE);
                requestBaseDataIfUserNotScrollToFragments(srl, presenter);
            }
        });


        /**
         * 虽然这里通过View.post方法在SwipeRefreshLayout初始化完毕后显示刷新，
         * 但是刷新监听中的onRefresh方法并不会被执行，所以下面手动调用一下
         */
//        srl.post(new Runnable() {
//            @Override
//            public void run() {
//                srl.setRefreshing(true);
//                isViewPrepared = true;
//                requestBaseData();
//            }
//        });
        isViewPrepared = true;
        requestBaseDataIfUserNotScrollToFragments(srl, presenter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void requestBaseData() {
        presenter.start(true);
    }


    public void refreshData(List<BaseMovie> newData, boolean shouldClean) {
        stopRefresh(srl);
        checkDataSize(newData, presenter.getLimit());
        if (shouldClean) {
            trendingList.clear();
        }
        trendingList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    public void onError() {
        showErrorInfo(srl, tvLoadedFailed, trendingList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
