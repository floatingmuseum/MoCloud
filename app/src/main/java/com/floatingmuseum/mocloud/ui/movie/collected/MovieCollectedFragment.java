package com.floatingmuseum.mocloud.ui.movie.collected;

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
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieCollectedFragment extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    public final static String MOVIE_COLLECTED_FRAGMENT = "MovieCollectedFragment";
    private List<BaseMovie> collectedList;
    private CollectedAdapter adapter;

    private MovieCollectedPresenter presenter;
    private GridLayoutManager manager;

    public static MovieCollectedFragment newInstance() {
        MovieCollectedFragment fragment = new MovieCollectedFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_trending, container, false);
        ButterKnife.bind(this, rootView);

        presenter = new MovieCollectedPresenter(this);

        initView();
        return rootView;
    }

    protected void initView() {
        collectedList = new ArrayList<>();
        adapter = new CollectedAdapter(collectedList);
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
                openMovieDetailActivity(collectedList.get(position).getMovie());
            }
        });
        requestBaseDataIfUserNotScrollToFragments(srl, presenter);
    }

    @Override
    protected void requestBaseData() {
        Logger.d("MovieCollectedFragment...First to see");
        srl.setRefreshing(true);
        presenter.start(true);
    }

    public void refreshData(List<BaseMovie> newData, boolean shouldClean) {
        checkDataSize(newData, presenter.getLimit());
        if (shouldClean) {
            collectedList.clear();
        }
        collectedList.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    public void stopRefresh() {
        stopRefresh(srl);
    }
}
