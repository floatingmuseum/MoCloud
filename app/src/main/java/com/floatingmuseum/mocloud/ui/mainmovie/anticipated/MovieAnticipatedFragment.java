package com.floatingmuseum.mocloud.ui.mainmovie.anticipated;

import android.os.Bundle;
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
public class MovieAnticipatedFragment extends BaseFragment implements MovieAnticipatedContract.View, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_ANTICIPATED_FRAGMENT = "MovieAnticipatedFragment";
    private List<BaseMovie> anticipatedList;
    private MovieAnticipatedAdapter adapter;

    private MovieAnticipatedPresenter presenter;
    private GridLayoutManager manager;

    public static MovieAnticipatedFragment newInstance() {
        MovieAnticipatedFragment fragment = new MovieAnticipatedFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieAnticipatedPresenter(this, Repository.getInstance());

        initView();
        return rootView;
    }

    protected void initView() {
        anticipatedList = new ArrayList<>();
        adapter =  new MovieAnticipatedAdapter(anticipatedList);
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

        rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                openMovieDetailActivity(anticipatedList.get(i).getMovie());
            }
        });
    }

    @Override
    protected void requestBaseData() {
        Logger.d("MovieAnticipatedFragment...First to see");
    }

    @Override
    public void onRefresh() {
        presenter.start(true);
    }

    @Override
    public void refreshData(List<BaseMovie> newData, boolean shouldClean) {
        if(newData.size()<presenter.getLimit()){
            alreadyGetAllData = true;
        }

        if(shouldClean){
            anticipatedList.clear();
        }
        anticipatedList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefresh() {
        stopRefresh(srl);
    }
}
