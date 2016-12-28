package com.floatingmuseum.mocloud.ui.mainmovie.trending;

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
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieTrendingFragment extends BaseFragment implements MovieTrendingContract.View {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_TRENDING_FRAGMENT = "MovieTrendingFragment";
    private List<BaseMovie> trendingList;
    private MovieTrendingAdapter adapter;

    private MovieTrendingPresenter presenter;
    private GridLayoutManager manager;

    public static MovieTrendingFragment newInstance() {
        MovieTrendingFragment fragment = new MovieTrendingFragment();
        return fragment;
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
        adapter =  new MovieTrendingAdapter(trendingList);
        rv.setHasFixedSize(true);
        manager = new GridLayoutManager(context,2);
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
                loadMore(manager,adapter,presenter,srl);
            }
        });

        rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                openMovieDetailActivity(trendingList.get(i).getMovie(),true);
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
        requestBaseDataIfUserNotScrollToFragments(srl,presenter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    protected void requestBaseData() {
        presenter.start(true);
    }

    @Override
    public void refreshData(List<BaseMovie> newData,boolean shouldClean) {
        if(newData.size()<presenter.getLimit()){
            alreadyGetAllData = true;
        }

        if(shouldClean){
            trendingList.clear();
        }
        trendingList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefresh() {
        stopRefresh(srl);
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
