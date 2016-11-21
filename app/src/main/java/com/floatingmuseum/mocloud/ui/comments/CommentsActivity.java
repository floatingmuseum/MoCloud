package com.floatingmuseum.mocloud.ui.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Comment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsActivity extends BaseActivity implements CommentsContract.View, SwipeRefreshLayout.OnRefreshListener {

    public static final String MOVIE_TITLE = "movie_title";

    private CommentsPresenter presenter;

    @BindView(R.id.rv_comments)
    RecyclerView rv_comments;
    @BindView(R.id.srl_comments)
    SwipeRefreshLayout srl_comments;

    public static final String MOVIE_ID = "movieID";
    private String movieId;
    private List<Comment> commentsData;
    private CommentsAdapter adapter;
    private LinearLayoutManager manager;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_comments;
    }
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        movieId = getIntent().getStringExtra(MOVIE_ID);
        String movieTitle = getIntent().getStringExtra(MOVIE_TITLE);
        actionBar.setTitle(movieTitle);
        presenter = new CommentsPresenter(this, Repository.getInstance());

        initView();
    }

    protected void initView() {
        commentsData = new ArrayList<>();
        adapter = new CommentsAdapter(commentsData);
        rv_comments.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        srl_comments.setOnRefreshListener(this);
        rv_comments.setLayoutManager(manager);
        rv_comments.setAdapter(adapter);

        srl_comments.post(new Runnable() {
            @Override
            public void run() {
                onRefresh();
                srl_comments.setRefreshing(true);
            }
        });

        rv_comments.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                loadMore(manager,adapter,movieId,presenter,srl_comments);
            }
        });

        rv_comments.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()){
                }
            }
        });
    }

    public void onBaseDataSuccess(List<Comment> comments) {
        if(comments.size()<presenter.getLimit()){
            alreadyGetAllData = true;
        }

        if(shouldClean){
            commentsData.clear();
        }
        commentsData.addAll(comments);
        adapter.notifyDataSetChanged();
        shouldClean = true;
    }

    @Override
    public void stopRefresh() {
        stopRefresh(srl_comments);
    }

    @Override
    public void onRefresh() {
        presenter.start(movieId,shouldClean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
