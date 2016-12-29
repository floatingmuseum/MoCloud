package com.floatingmuseum.mocloud.ui.comments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
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
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyStoreBuilderParameters;

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
    @BindView(R.id.comment_box)
    EditText commentBox;
    @BindView(R.id.iv_reply)
    ImageView ivReply;
    @BindView(R.id.isSpoiler)
    CheckBox isSpoiler;

    public static final String MAIN_COMMENT = "main_comment";
    private Comment mainCommentContent;
    private SingleCommentPresenter presenter;
    private List<Comment> repliesList;
    private SingleCommentAdapter adapter;
    private CommentReplyDialog replyDialog;
    private int likes;
    private int replies;

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_single_comment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new SingleCommentPresenter(this);
        mainCommentContent = getIntent().getParcelableExtra(MAIN_COMMENT);
        initView();
        presenter.getData(mainCommentContent.getId());
    }

    @Override
    protected void initView() {
        Logger.d("喜欢数:" + likes + "...回复数:" + replies);
        likes = mainCommentContent.getLikes();
        replies = mainCommentContent.getReplies();
        tvUsername.setText(mainCommentContent.getUser().getUsername());
        tvUpdatetime.setText(TimeUtil.formatGmtTime(mainCommentContent.getUpdated_at()));
        tvCommentsLikes.setText(String.valueOf(likes));
        tvCommentsReplies.setText(String.valueOf(replies));
        tvComment.setMaxLines(5);
        tvComment.setText(mainCommentContent.getComment());
        Image image = mainCommentContent.getUser().getImages();
        if (image != null && image.getAvatar() != null) {
            ImageLoader.loadDontAnimate(this, image.getAvatar().getFull(), ivUserhead, R.drawable.default_userhead);
        }
        ivUserhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(SingleCommentActivity.this, mainCommentContent.getUser());
            }
        });

        repliesList = new ArrayList<>();
        adapter = new SingleCommentAdapter(repliesList);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvReplies.setLayoutManager(manager);
        rvReplies.setAdapter(adapter);

        rvReplies.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                String replySomeOne = "@" + repliesList.get(i).getUser().getUsername() + " ";
                commentBox.setText(replySomeOne);
                KeyboardUtil.showSoftInput(commentBox);
//                openKeyBoard(replySomeOne);
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                switch (view.getId()) {
                    case R.id.iv_userhead:
                        openUserActivity(SingleCommentActivity.this, repliesList.get(position).getUser());
                        break;
                }
            }
        });

        ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReply();
            }
        });
    }

    private void openKeyBoard(String replySomeOne) {
        commentBox.setText(replySomeOne);
        commentBox.setSelection(replySomeOne.length());
        commentBox.setFocusable(true);
        commentBox.setFocusableInTouchMode(true);
        commentBox.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(commentBox, 0);
    }

    private void sendReply() {
        String replyContent = commentBox.getText().toString();
        Logger.d("回复内容:" + replyContent + "...isSpoiler" + isSpoiler.isChecked());
        if (!StringUtil.checkReplyContent(replyContent)) {
            ToastUtil.showToast(R.string.comment_tip1);
            return;
        }
        Reply reply = new Reply();
        reply.setSpoiler(isSpoiler.isChecked());
        reply.setComment(replyContent);
        presenter.sendReply(mainCommentContent.getId(), reply);
    }

    private void openDialog(Comment comment) {
        replyDialog = new CommentReplyDialog(this, presenter, comment);
        replyDialog.show();
    }

    public void onBaseDataSuccess(List<Comment> replies) {
        if (replies == null && replies.size() == 0) {
            return;
        }

        repliesList.clear();
        repliesList.addAll(replies);
        adapter.notifyDataSetChanged();
    }

    public void onSendReplySuccess(Comment comment) {
        Logger.d("sendReply...onSendReplySuccess:" + comment.getComment());
        repliesList.add(comment);
        adapter.notifyItemInserted(repliesList.indexOf(comment));
        replies++;
        tvCommentsReplies.setText(replies);
        ToastUtil.showToast(R.string.reply_success);
        KeyboardUtil.hideSoftInput(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unSubscription();
    }
}
