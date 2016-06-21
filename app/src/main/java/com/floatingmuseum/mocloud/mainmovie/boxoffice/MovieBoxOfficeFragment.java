package com.floatingmuseum.mocloud.mainmovie.boxoffice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMoviePresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MoviePresenterModule;
import com.floatingmuseum.mocloud.mainmovie.collected.MovieCollectedContract;
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieBoxOfficeFragment extends BaseFragment implements MovieBoxOfficeContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_BOXOFFICE_FRAGMENT = "MovieBoxOfficeFragment";
    private List<BaseMovie> boxOfficeList;
    private MovieBoxOfficeAdapter adapter;
    @Inject
    MovieBoxOfficePresenter mBoxOfficePresenter;
    private GridLayoutManager manager;

    public static MovieBoxOfficeFragment newInstance() {
        MovieBoxOfficeFragment fragment = new MovieBoxOfficeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        DaggerMoviePresenterComponent.builder()
                .moviePresenterModule(new MoviePresenterModule(this))
                .build()
                .inject(this);

        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        boxOfficeList = new ArrayList<>();
        adapter =  new MovieBoxOfficeAdapter(boxOfficeList,context);
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
    }

    @Override
    public void onRefresh() {
        mBoxOfficePresenter.start();
    }

    @Override
    public void refreshData(List<BaseMovie> newData) {
        boxOfficeList.clear();
        boxOfficeList.addAll(newData);
        adapter.notifyDataSetChanged();
        srl.setRefreshing(false);
    }

    @Override
    public void stopRefresh() {
        if(srl!=null){
            srl.setRefreshing(false);
        }
    }
}
