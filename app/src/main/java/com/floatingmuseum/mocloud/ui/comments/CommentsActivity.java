package com.floatingmuseum.mocloud.ui.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
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

        rv_comments.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()){
                    case R.id.tv_spoiler:
//                        view.setVisibility(View.GONE);
                        break;
                    case R.id.fl_comment:
                        view.findViewById(R.id.tv_spoiler).setVisibility(View.GONE);
                        view.findViewById(R.id.tv_comment).setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

//        adapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Logger.d("onItemChildClick");
//                switch (view.getId()){
//                    case R.id.fl_comment:
//                        Logger.d("on评论被点击");
//                        View tv_spoiler = view.findViewById(R.id.tv_spoiler);
//                        View tv_comment = view.findViewById(R.id.tv_comment);
//                        tv_spoiler.setVisibility(View.GONE);
//                        AlphaAnimation alphaAnim = new AlphaAnimation(0.0f,1.0f);
//                        alphaAnim.setDuration(3000);
//                        tv_comment.setAnimation(alphaAnim);
//                        break;
//                }
//            }
//        });
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
        ButterKnife.unbind(this);
    }
}
