package com.floatingmuseum.mocloud.ui.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.dagger.presenter.CommentsPresenterModule;
import com.floatingmuseum.mocloud.dagger.presenter.DaggerCommentsPresenterComponent;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsActivity extends BaseActivity implements CommentsContract.View, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    CommentsPresenter presenter;

    @Bind(R.id.rv_comments)
    RecyclerView rv_comments;
    @Bind(R.id.srl_comments)
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

        DaggerCommentsPresenterComponent.builder()
                .repoComponent(getRepoComponent())
                .commentsPresenterModule(new CommentsPresenterModule(this))
                .build()
                .inject(this);

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
    }

    public void onBaseDataSuccess(List<Comment> comments) {
        if(comments.size()<presenter.getLimit()){
            alreadyGetAllData = true;
        }

        if(shouldClean){
            commentsData.clear();
        }
        commentsData.addAll(comments);
        Logger.d("评论数量："+commentsData.size()+"...单次请求评论数量："+comments.size());
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
        ButterKnife.unbind(this);
    }
}