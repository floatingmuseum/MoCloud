package com.floatingmuseum.mocloud.ui.mainmovie.played;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMoviePresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MoviePresenterModule;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoviePlayedFragment extends BaseFragment implements MoviePlayedContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.OnRecyclerViewItemClickListener {

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_PLAYED_FRAGMENT = "MoviePlayedFragment";
    private List<BaseMovie> playedList;
    private MoviePlayedAdapter adapter;
    @Inject
    MoviePlayedPresenter presenter;
    private GridLayoutManager manager;

    public static MoviePlayedFragment newInstance() {
        MoviePlayedFragment fragment = new MoviePlayedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        DaggerMoviePresenterComponent.builder()
                .moviePresenterModule(new MoviePresenterModule(this))
                .repoComponent(moCloud.getRepoComponent())
                .build().inject(this);

        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        playedList = new ArrayList<>();
        adapter =  new MoviePlayedAdapter(playedList);
        rv.setHasFixedSize(true);
        manager = new GridLayoutManager(context,3);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        srl.setOnRefreshListener(this);
        /**
         * 虽然这里通过View.post方法在SwipeRefreshLayout初始化完毕后显示刷新，
         * 但是刷新监听中的onRefresh方法并不会被执行，所以下面手动调用一下
         */
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
        onRefresh();
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(manager,adapter,presenter,srl);
            }
        });
        adapter.setOnRecyclerViewItemClickListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void refreshData(List<BaseMovie> newData,boolean shouldClean) {
        if(newData.size()<10){
            alreadyGetAllData = true;
        }

        if(shouldClean){
            playedList.clear();
        }
        playedList.addAll(newData);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void stopRefresh() {
        stopRefresh(srl);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRefresh() {
        presenter.start(true);
    }

    @Override
    public void onItemClick(View view, int i) {
        openMovieDetailActivity(playedList.get(i).getMovie().getIds().getSlug());
    }
}

