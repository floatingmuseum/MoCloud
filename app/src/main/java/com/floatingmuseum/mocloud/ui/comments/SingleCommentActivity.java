package com.floatingmuseum.mocloud.ui.comments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.floatingmuseum.mocloud.base.BaseCommentsActivity;
import com.floatingmuseum.mocloud.base.BaseCommentsItemAdapter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
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

public class SingleCommentActivity extends BaseCommentsActivity {

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
    private BaseCommentsItemAdapter adapter;
    private int likes;
    private int replies;
    //    private TextView tvCommentReplies;
    private String username;

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
        likes = mainCommentContent.getLikes();
        replies = mainCommentContent.getReplies();
        username = MoCloudUtil.getUsername(mainCommentContent.getUser());
        Logger.d("喜欢数:" + likes + "...回复数:" + replies);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvReplies.setLayoutManager(manager);
        CardView headerView = (CardView) LayoutInflater.from(this).inflate(R.layout.comment_item, rvReplies, false);
        initCommentItem(this, headerView, mainCommentContent, true);
//        initHeaderView(headerView);

        repliesList = new ArrayList<>();
        adapter = new BaseCommentsItemAdapter(repliesList, username);
        adapter.addHeaderView(headerView);

        rvReplies.setAdapter(adapter);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCommentReplyBox(mainCommentContent.getUser());
            }
        });

        rvReplies.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                String username = MoCloudUtil.getUsername(repliesList.get(i).getUser());
//                String replySomeOne = "@" + username + " ";
//                commentBox.setText(replySomeOne);
//                commentBox.setSelection(replySomeOne.length());
//                KeyboardUtil.showSoftInput(commentBox);
                initCommentReplyBox(repliesList.get(i).getUser());
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

    private void initCommentReplyBox(User user) {
        String username = MoCloudUtil.getUsername(user);
        String replySomeOne = "@" + username + " ";
        commentBox.setText(replySomeOne);
        commentBox.setSelection(replySomeOne.length());
        KeyboardUtil.showSoftInput(commentBox);
    }

    private void initHeaderView(View headerView) {
//        CircleImageView ivUserhead = (CircleImageView) headerView.findViewById(R.id.iv_userhead);
//        TextView tvUsername = (TextView) headerView.findViewById(R.id.tv_username);
//        TextView tvCreateTime = (TextView) headerView.findViewById(R.id.tv_createtime);
//        TextView tvUpdateTime = (TextView) headerView.findViewById(R.id.tv_updatetime);
//        ImageView ivCommentLikes = (ImageView) headerView.findViewById(R.id.iv_comment_likes);
//        TextView tvCommentLikes = (TextView) headerView.findViewById(R.id.tv_comment_likes);
//        tvCommentReplies = (TextView) headerView.findViewById(R.id.tv_comments_replies);
//        TextView tvComment = (TextView) headerView.findViewById(R.id.tv_comment);
//
//        tvUsername.setText(username);
//        tvUsername.setTextColor(ResUtil.getColor(R.color.comment_owner,null));
//        tvUsername.setTypeface(null, Typeface.BOLD);
//        tvCreateTime.setText(TimeUtil.formatGmtTime(mainCommentContent.getCreated_at()));
//        tvUpdateTime.setText(TimeUtil.formatGmtTime(mainCommentContent.getUpdated_at()));
//        tvCommentLikes.setText(String.valueOf(likes));
//        tvCommentReplies.setText(String.valueOf(replies));
//        tvComment.setText(mainCommentContent.getComment());
//        Image image = mainCommentContent.getUser().getImages();
//        if (image != null && image.getAvatar() != null) {
//            ImageLoader.loadDontAnimate(this, image.getAvatar().getFull(), ivUserhead, R.drawable.default_userhead);
//        }
//        ivUserhead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openUserActivity(SingleCommentActivity.this, mainCommentContent.getUser());
//            }
//        });
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

    public void onBaseDataSuccess(List<Comment> replies) {
        if (replies == null && replies.size() == 0) {
            return;
        }

        repliesList.clear();
        repliesList.addAll(replies);
        adapter.notifyDataSetChanged();
    }

    private void sendReply() {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
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

    public void onSendReplySuccess(Comment comment) {
        Logger.d("sendReply...onSendReplySuccess:" + comment.getComment());
        repliesList.add(comment);
        adapter.notifyItemInserted(repliesList.indexOf(comment));
        replies++;
        tvCommentReplies.setText(replies);
        ToastUtil.showToast(R.string.reply_success);
        KeyboardUtil.hideSoftInput(this);
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
