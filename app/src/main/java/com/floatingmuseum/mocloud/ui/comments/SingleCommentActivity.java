package com.floatingmuseum.mocloud.ui.comments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.base.BaseCommentsActivity;
import com.floatingmuseum.mocloud.base.BaseCommentsItemAdapter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.bus.CommentLikeEvent;
import com.floatingmuseum.mocloud.data.bus.EventBusManager;
import com.floatingmuseum.mocloud.data.bus.SyncEvent;
import com.floatingmuseum.mocloud.data.entity.Colors;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.data.entity.Reply;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.ui.user.UserActivity;
import com.floatingmuseum.mocloud.utils.ColorUtil;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

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

    @BindView(R.id.rl_comment_container)
    RelativeLayout rlCommentContainer;
    @BindView(R.id.ll_comments)
    LinearLayout llComments;
    @BindView(R.id.rv_replies)
    RecyclerView rvReplies;
    @BindView(R.id.comment_box)
    EditText commentBox;
    @BindView(R.id.iv_reply)
    ImageView ivReply;
    @BindView(R.id.isSpoiler)
    CheckBox isSpoiler;
    @BindView(R.id.tv_spoiler)
    TextView tvSpoiler;


    private Comment mainCommentContent;
    private SingleCommentPresenter presenter;
    private List<Comment> repliesList;
    private BaseCommentsItemAdapter adapter;
    private int likes;
    private int replies;
    //    private TextView tvCommentReplies;
    private String username;
    private Colors mainColors;
    private Colors itemColors;
    private String movieTitle;
    private int commentLikePosition;
    private CardView headerView;


    @Override
    protected int currentLayoutId() {
        return R.layout.activity_single_comment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        presenter = new SingleCommentPresenter(this);
        movieTitle = getIntent().getStringExtra(MOVIE_TITLE);
        mainCommentContent = getIntent().getParcelableExtra(MAIN_COMMENT);
        mainColors = getIntent().getParcelableExtra(MAIN_COLORS);
        itemColors = getIntent().getParcelableExtra(ITEM_COLORS);
        Logger.d("Colors:" + mainColors);
        initView();
        if (mainColors != null && itemColors != null) {
            initColors();
        }
        presenter.getData(mainCommentContent.getId());
    }

    @Override
    protected void initView() {
        actionBar.setTitle(movieTitle);
        likes = mainCommentContent.getLikes();
        replies = mainCommentContent.getReplies();
        username = MoCloudUtil.getUsername(mainCommentContent.getUser());
        Logger.d("喜欢数:" + likes + "...回复数:" + replies);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvReplies.setLayoutManager(manager);
        headerView = (CardView) LayoutInflater.from(this).inflate(R.layout.comment_item, rvReplies, false);

        if (mainColors != null && itemColors != null) {
            Palette.Swatch commentItemSwatch = new Palette.Swatch(itemColors.getRgb(), itemColors.getPopulation());
            initCommentItem(this, headerView, mainCommentContent, null, commentItemSwatch, null, true);
        } else {
            initCommentItem(this, headerView, mainCommentContent, null, null, null, true);
        }

        repliesList = new ArrayList<>();
        adapter = new BaseCommentsItemAdapter(repliesList, username, itemColors);
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
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Logger.d("Username:" + repliesList.get(position).getUser() + "...position:" + position);
                initCommentReplyBox(repliesList.get(position).getUser());
            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                super.onItemChildClick(adapter, view, position);
                Comment comment = repliesList.get(position);
                switch (view.getId()) {
                    case R.id.iv_userhead:
                        openUserActivity(SingleCommentActivity.this, comment.getUser());
                        break;
                    case R.id.ll_comment_likes:
                        //记录点赞位置
                        commentLikePosition = position;
                        syncCommentLike(comment.isLike(), comment);
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

    private void initColors() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.setStatusBarColor(ColorUtil.darkerColor(mainColors.getRgb(), 0.4));
        }
        toolbar.setBackgroundColor(ColorUtil.darkerColor(mainColors.getRgb(), 0.2));
        rlCommentContainer.setBackgroundColor(mainColors.getRgb());
        llComments.setBackgroundColor(ColorUtil.darkerColor(mainColors.getRgb(), 0.1));
        tvSpoiler.setTextColor(mainColors.getTitleTextColor());
        commentBox.setTextColor(mainColors.getBodyTextColor());
        commentBox.setHintTextColor(mainColors.getTitleTextColor());
    }

    private void initCommentReplyBox(User user) {
        String replySomeOne = "@" + user.getIds().getSlug() + " ";
        commentBox.setText(replySomeOne);
        commentBox.setSelection(replySomeOne.length());
        KeyboardUtil.showSoftInput(commentBox);
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

    public void onSendCommentSuccess(Comment comment) {
        Logger.d("sendReply...onSendReplySuccess:" + comment.getComment());
        repliesList.add(comment);
        adapter.notifyItemInserted(repliesList.indexOf(comment));
//        rvReplies.scrollToPosition();
        replies++;
        tvCommentReplies.setText(replies);
        ToastUtil.showToast(R.string.reply_success);
        resetCommentBox(commentBox, isSpoiler);
    }

    @Override
    protected void syncCommentLike(boolean isLike, Comment comment) {
        presenter.syncCommentLike(isLike, comment, presenter);
    }

    public void onAddCommentToLikesSucceed(long commentId) {
        Comment comment = repliesList.get(commentLikePosition);
        if (comment.getId() == commentId) {
            comment.setLike(true);
            int likes = comment.getLikes();
            comment.setLikes(++likes);
//            adapter.notifyItemChanged(commentLikePosition);
        } else {
            updateCommentLikesView(true, headerView, mainCommentContent);
            EventBus.getDefault().post(new CommentLikeEvent(commentId, true, mainCommentContent.getLikes()));
        }
        adapter.notifyDataSetChanged();
    }

    public void onRemoveCommentFromLikesSucceed(long commentId) {
        Comment comment = repliesList.get(commentLikePosition);
        if (comment.getId() == commentId) {
            comment.setLike(false);
            int likes = comment.getLikes();
            comment.setLikes(--likes);
//            adapter.notifyItemChanged(commentLikePosition);
        } else {
            updateCommentLikesView(false, headerView, mainCommentContent);
            //只有headerView的状态变化后需要通知MovieDetailActivity评论的like状态
            EventBus.getDefault().post(new CommentLikeEvent(commentId, false, mainCommentContent.getLikes()));
        }
        adapter.notifyDataSetChanged();
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
