package com.floatingmuseum.mocloud.mainmovie.collected;

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
import com.floatingmuseum.mocloud.model.entity.BaseMovie;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieCollectedFragment extends BaseFragment implements MovieCollectedContract.View, SwipeRefreshLayout.OnRefreshListener{

    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_COLLECTED_FRAGMENT = "MovieCollectedFragment";
    private List<BaseMovie> collectedList;
    private MovieCollectedAdapter adapter;
    private MovieCollectedContract.Presenter mCollectedPresenter;
    private GridLayoutManager manager;
    private boolean alreadyGetAllData = false;

    public static MovieCollectedFragment newInstance() {
        MovieCollectedFragment fragment = new MovieCollectedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);
        new MovieCollectedPresenter(this);
        initRecyclerView();
        return rootView;
    }

    private void initRecyclerView() {
        collectedList = new ArrayList<>();
        adapter =  new MovieCollectedAdapter(collectedList,context);
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
                int lastItemPosition = manager.findLastVisibleItemPosition();
                if(lastItemPosition==(adapter.getItemCount()-1)&&!alreadyGetAllData){
                    mCollectedPresenter.start(false);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        mCollectedPresenter.start(true);
    }

    @Override
    public void refreshData(List<BaseMovie> newData, boolean shouldClean) {
        if(newData.size()<10){
            alreadyGetAllData = true;
        }

        if(shouldClean){
            collectedList.clear();
        }
        collectedList.addAll(newData);
        adapter.notifyDataSetChanged();

        srl.setRefreshing(false);
    }

    @Override
    public void setPresenter(MovieCollectedContract.Presenter presenter) {
        mCollectedPresenter = presenter;
    }

    @Override
    public void stopRefresh() {
        if(srl!=null){
            srl.setRefreshing(false);
        }
    }
}
