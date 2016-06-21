package com.floatingmuseum.mocloud.mainmovie.popular;

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
import com.floatingmuseum.mocloud.dagger.presenter.DaggerMoviePresenterComponent;
import com.floatingmuseum.mocloud.dagger.presenter.MoviePresenterModule;
import com.floatingmuseum.mocloud.model.entity.Movie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoviePopularFragment extends BaseFragment implements MoviePopularContract.View,SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_POPILAR_FRAGMENT = "MoviePopularFragment";
    private List<Movie> popularList;
    private MoviePopularAdapter adapter;
    @Inject
    MoviePopularPresenter mPopularPresenter;
    private GridLayoutManager manager;
    private boolean alreadyGetAllData = false;
    public static MoviePopularFragment newInstance() {
        MoviePopularFragment fragment = new MoviePopularFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);
//        new MoviePopularPresenter(this);

        DaggerMoviePresenterComponent.builder()
                .moviePresenterModule(new MoviePresenterModule(this))
                .build()
                .inject(this);

        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        popularList = new ArrayList<>();
        adapter =  new MoviePopularAdapter(popularList,context);
        rv.setHasFixedSize(true);
        manager = new GridLayoutManager(context,3);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        srl.setOnRefreshListener(this);

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
                int lastItemPosition = manager.findLastVisibleItemPosition();
                if(lastItemPosition==(adapter.getItemCount()-1)&&!alreadyGetAllData){
                    mPopularPresenter.start(false);
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void refreshData(List<Movie> newData, boolean shouldClean) {
        if(newData.size()<10){
            alreadyGetAllData = true;
        }

        if(shouldClean){
            popularList.clear();
        }
        popularList.addAll(newData);
        adapter.notifyDataSetChanged();

        srl.setRefreshing(false);
    }

    @Override
    public void stopRefresh() {
        if(srl!=null){
            srl.setRefreshing(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onRefresh() {
        mPopularPresenter.start(true);
    }
}
