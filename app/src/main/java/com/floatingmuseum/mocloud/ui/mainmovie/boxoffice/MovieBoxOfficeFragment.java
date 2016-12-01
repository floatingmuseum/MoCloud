package com.floatingmuseum.mocloud.ui.mainmovie.boxoffice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
public class MovieBoxOfficeFragment extends BaseFragment implements MovieBoxOfficeContract.View {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_BOXOFFICE_FRAGMENT = "MovieBoxOfficeFragment";
    private List<BaseMovie> boxOfficeList;
    private MovieBoxOfficeAdapter adapter;

    private MovieBoxOfficePresenter presenter;
//    private GridLayoutManager manager;
    private LinearLayoutManager manager;


    public static MovieBoxOfficeFragment newInstance() {
        MovieBoxOfficeFragment fragment = new MovieBoxOfficeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieBoxOfficePresenter(this, Repository.getInstance());

        initView();
        return rootView;
    }

    protected void initView() {
        boxOfficeList = new ArrayList<>();
        adapter =  new MovieBoxOfficeAdapter(boxOfficeList);
        rv.setHasFixedSize(true);
//        manager = new GridLayoutManager(context,2);
        manager = new LinearLayoutManager(context);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.start(true);
            }
        });

        rv.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                openMovieDetailActivity(boxOfficeList.get(i).getMovie());
            }
        });

        requestBaseDataIfUserNotScrollToFragments(srl,presenter);

//        isViewPrepared = true;
    }

    @Override
    protected void requestBaseData() {
        srl.setRefreshing(true);
        presenter.start(true);
    }

    @Override
    public void refreshData(List<BaseMovie> newData) {
        boxOfficeList.clear();
        boxOfficeList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefresh() {
        stopRefresh(srl);
    }
}
