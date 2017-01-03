package com.floatingmuseum.mocloud.ui.comments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Ids;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.Sharing;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsActivity extends BaseActivity implements CommentsContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_comments)
    RecyclerView rv_comments;
    @BindView(R.id.srl_comments)
    SwipeRefreshLayout srl_comments;
    @BindView(R.id.isSpoiler)
    CheckBox isSpoiler;
    @BindView(R.id.comment_box)
    EditText commentBox;
    @BindView(R.id.iv_reply)
    ImageView ivReply;

    public static final String MOVIE_OBJECT = "movie_object";

    private CommentsPresenter presenter;
    private TmdbMovieDetail movie;
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

        movie = getIntent().getParcelableExtra(MOVIE_OBJECT);
        actionBar.setTitle(movie.getTitle());
        presenter = new CommentsPresenter(this);

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
                loadMore(manager, adapter, movie.getImdb_id(), presenter, srl_comments);
            }
        });

        rv_comments.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Logger.d("条目被点击");
                Intent intent = new Intent(CommentsActivity.this, SingleCommentActivity.class);
                intent.putExtra(SingleCommentActivity.MAIN_COMMENT, commentsData.get(i));
                startActivity(intent);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.iv_userhead:
                        Logger.d("头像被点击");
                        openUserActivity(CommentsActivity.this,commentsData.get(position).getUser());
                        break;
                    case R.id.tv_comments_likes:
                        break;
                }
            }
        });

        ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    private void sendComment() {
        String replyContent = commentBox.getText().toString();
        Logger.d("回复内容:" + replyContent + "...isSpoiler" + isSpoiler.isChecked());
        if (!StringUtil.checkReplyContent(replyContent)) {
            ToastUtil.showToast(R.string.comment_tip1);
            return;
        }
        Comment comment = new Comment();
        comment.setSpoiler(isSpoiler.isChecked());
        comment.setComment(replyContent);
//        comment.setMovie(movie);
        presenter.sendComment(comment,movie.getImdb_id());
    }

    public void onBaseDataSuccess(List<Comment> comments) {
        if (comments.size() < presenter.getLimit()) {
            alreadyGetAllData = true;
        }

        if (shouldClean) {
            commentsData.clear();
        }
        commentsData.addAll(comments);
        adapter.notifyDataSetChanged();
        shouldClean = true;
    }

    public void onSendCommentSuccess(Comment comment) {
        Logger.d("sendComment...onSendCommentSuccess:"+comment.getComment());
        ToastUtil.showToast(R.string.reply_success);
        commentsData.add(0,comment);
        adapter.notifyItemInserted(0);
        KeyboardUtil.hideSoftInput(this);
    }

    @Override
    public void stopRefresh() {
        stopRefresh(srl_comments);
    }

    @Override
    public void onRefresh() {
        presenter.start(movie.getImdb_id(), shouldClean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
