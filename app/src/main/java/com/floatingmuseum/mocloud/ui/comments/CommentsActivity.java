package com.floatingmuseum.mocloud.ui.comments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.entity.Comment;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsActivity extends BaseActivity {

    @Bind(R.id.rv_comments)
    RecyclerView rv_comments;

    public static final String COMMENTS_DATA = "comments";
    private List<Comment> commentsData;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_comments;
    }

    // TODO: 2016/9/1 详情页只获取有限的3个评论，评论页获取全部评论。可以去掉序列化接口，以及intent传递集合。 
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        commentsData = (List<Comment>) getIntent().getExtras().get(COMMENTS_DATA);
        initUI();
    }

    private void initUI() {
        CommentsAdapter adapter = new CommentsAdapter(commentsData);
        rv_comments.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rv_comments.setLayoutManager(manager);
        rv_comments.setAdapter(adapter);
    }
}
