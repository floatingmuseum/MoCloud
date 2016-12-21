package com.floatingmuseum.mocloud.ui.comments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Floatingmuseum on 2016/12/20.
 */

public class SingleCommentActivity extends BaseActivity {


    @BindView(R.id.iv_userhead)
    CircleImageView ivUserhead;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_updatetime)
    TextView tvUpdatetime;
    @BindView(R.id.tv_spoiler_tip)
    TextView tvSpoilerTip;
    @BindView(R.id.tv_comments_likes)
    TextView tvCommentsLikes;
    @BindView(R.id.iv_replies)
    ImageView ivReplies;
    @BindView(R.id.tv_comments_replies)
    TextView tvCommentsReplies;
    @BindView(R.id.comment_title)
    LinearLayout commentTitle;
    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.main_comment)
    CardView mainComment;
    @BindView(R.id.rv_replies)
    RecyclerView rvReplies;

    public static final String MAIN_COMMENT = "main_comment";
    private Comment mainCommentContent;
    private SingleCommentPresenter presenter;
    private List<Comment> repliesList;
    private SingleCommentAdapter adapter;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_single_comment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new SingleCommentPresenter(this, Repository.getInstance());
        mainCommentContent = getIntent().getParcelableExtra(MAIN_COMMENT);
        initView();
        presenter.getData(mainCommentContent.getId());
    }

    @Override
    protected void initView() {
        Logger.d("Comment:"+mainCommentContent);
        tvUsername.setText(mainCommentContent.getUser().getUsername());
        tvUpdatetime.setText(TimeUtil.formatGmtTime(mainCommentContent.getUpdated_at()));
        tvCommentsLikes.setText("" + mainCommentContent.getLikes());
        tvCommentsReplies.setText("" + mainCommentContent.getReplies());
        tvComment.setMaxLines(5);
        tvComment.setText(mainCommentContent.getComment());
        Image image = mainCommentContent.getUser().getImages();
        if (image != null && image.getAvatar() != null) {
            ImageLoader.loadDontAnimate(this, image.getAvatar().getFull(), ivUserhead, R.drawable.default_userhead);
//                Glide.with(this).load(image.getAvatar().getFull()).placeholder(R.drawable.default_userhead).into(iv_userhead);
        }

        repliesList = new ArrayList<>();
        adapter = new SingleCommentAdapter(repliesList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvReplies.setLayoutManager(manager);
        rvReplies.setAdapter(adapter);

        rvReplies.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                openDialog(repliesList.get(i));
            }
        });
    }

    private void openDialog(Comment comment) {
        final CommentReplyDialog replyDialog = new CommentReplyDialog(this);
        replyDialog.show();
    }

    public void onBaseDataSuccess(List<Comment> replies){
        if (replies==null && replies.size()==0){
            return;
        }

        repliesList.clear();
        repliesList.addAll(replies);
        adapter.notifyDataSetChanged();
    }
}
