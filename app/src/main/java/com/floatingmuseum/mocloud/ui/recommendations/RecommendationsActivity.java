package com.floatingmuseum.mocloud.ui.recommendations;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class RecommendationsActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_recommendations)
    RecyclerView rvRecommendations;
    @BindView(R.id.srl_recommendations)
    SwipeRefreshLayout srlRecommendations;

    private RecommendationsPresenter presenter;
    private List<Movie> data;
    private RecommendationsAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_recommendations;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        presenter = new RecommendationsPresenter(this);
        initView();
    }

    @Override
    protected void initView() {
        rvRecommendations.setHasFixedSize(true);
        data = new ArrayList<>();
        manager = new LinearLayoutManager(this);
        rvRecommendations.setLayoutManager(manager);
        adapter = new RecommendationsAdapter(data);
        rvRecommendations.setAdapter(adapter);
        srlRecommendations.setOnRefreshListener(this);
        srlRecommendations.post(new Runnable() {
            @Override
            public void run() {
                triggerRefreshRequest();
            }
        });

        OnItemSwipeListener swipeListener = new OnItemSwipeListener(){

            @Override
            public void onItemSwipeStart(RecyclerView.ViewHolder viewHolder, int i) {
                Logger.d("onItemSwipeStart:...position:"+i);
            }

            @Override
            public void clearView(RecyclerView.ViewHolder viewHolder, int i) {
                Logger.d("clearView:...position"+i);
            }

            @Override
            public void onItemSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                Movie movie = data.get(i);
                Logger.d("onItemSwiped:...position"+i+"...Movie:"+movie.getTitle());
                presenter.hideMovie(data.get(i).getIds().getSlug());
            }

            @Override
            public void onItemSwipeMoving(Canvas canvas, RecyclerView.ViewHolder viewHolder, float dx, float dy, boolean isCurrentlyActive) {
                Logger.d("onItemSwipeStart:...dx:"+dx+"...dy:"+dy+"...isCurrentlyActive:"+isCurrentlyActive);
            }
        };

        ItemDragAndSwipeCallback mItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(adapter);
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mItemDragAndSwipeCallback);
        mItemTouchHelper.attachToRecyclerView(rvRecommendations);
        mItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START| ItemTouchHelper.END);
        adapter.enableSwipeItem();
        adapter.setOnItemSwipeListener(swipeListener);
        rvRecommendations.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Logger.d("SimpleOnItemClick:"+data.get(i).getTitle()+"...position:"+i+"...size:"+data.size());
            }
        });
    }

    private void triggerRefreshRequest() {
        srlRecommendations.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {
        presenter.getData();
    }

    public void onBaseDataSuccess(List<Movie> movies) {
        srlRecommendations.setRefreshing(false);
        data.clear();
        data.addAll(movies);
        adapter.notifyDataSetChanged();
    }

    public void onHideMovieSuccess(){
        ToastUtil.showToast("Hide success.");
    }

    @Override
    protected void onError(Exception e) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
    }
}
