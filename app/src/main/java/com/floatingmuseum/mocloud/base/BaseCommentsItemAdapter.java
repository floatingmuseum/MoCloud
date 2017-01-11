package com.floatingmuseum.mocloud.base;

import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/11.
 */

public class BaseCommentsItemAdapter extends BaseQuickAdapter<Comment> {

    private String commentOwner;

    public BaseCommentsItemAdapter(List<Comment> commentsData) {
        super(R.layout.comment_item, commentsData);
    }

    public BaseCommentsItemAdapter(List<Comment> commentsData, String commentOwner) {
        super(R.layout.comment_item, commentsData);
        this.commentOwner = commentOwner;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
        User user = comment.getUser();
        String username = MoCloudUtil.getUsername(user);
        baseViewHolder.setText(R.id.tv_createtime, TimeUtil.formatGmtTime(comment.getCreated_at()))
                .setText(R.id.tv_updatetime, "---updated at " + TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comment_likes, "" + comment.getLikes())
                .setText(R.id.tv_comment, comment.getComment())
                .setText(R.id.tv_username, username)
                .addOnClickListener(R.id.iv_userhead)
                .setVisible(R.id.tv_spoiler_tip, comment.isSpoiler() ? true : false)
                .setVisible(R.id.tv_review_tip, comment.isReview() ? true : false)
                .setVisible(R.id.ll_tip, comment.isSpoiler() || comment.isReview() ? true : false)
                .setVisible(R.id.tv_updatetime, comment.getCreated_at().equals(comment.getUpdated_at()) ? false : true);
        ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
        ImageLoader.loadDontAnimate(mContext, MoCloudUtil.getUserAvatar(user), iv_userhead, R.drawable.default_userhead);
        TextView tvUsername = baseViewHolder.getView(R.id.tv_username);
        if (commentOwner == null) {
            return;
        }
        Logger.d("楼主:"+commentOwner+"...当前楼昵称:"+username);
        if (username.equals(commentOwner)) {
            tvUsername.setTypeface(tvUsername.getTypeface(), Typeface.BOLD);
            tvUsername.setTextColor(ResUtil.getColor(R.color.comment_owner, null));
        } else {
            tvUsername.setTypeface(Typeface.DEFAULT);
            tvUsername.setTextColor(ResUtil.getColor(R.color.comment_user, null));
        }
    }
}
