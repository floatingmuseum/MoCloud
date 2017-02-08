package com.floatingmuseum.mocloud.ui.comments;

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

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/20.
 */

public class SingleCommentAdapter extends BaseQuickAdapter<Comment,BaseViewHolder> {

    private String commentOwner;

    public SingleCommentAdapter(List<Comment> commentsData, String commentOwner) {
        super(R.layout.comment_item, commentsData);
        this.commentOwner = commentOwner;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
//        if (comment.isReview()) {
//            baseViewHolder.setBackgroundColor(R.id.comment_title, ResUtil.getColor(R.color.comment_review_title_grey, null));
//            baseViewHolder.setBackgroundColor(R.id.tv_comment, ResUtil.getColor(R.color.comment_review_content_grey, null));
//        }

        baseViewHolder.setText(R.id.tv_updatetime, TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comment_likes, "" + comment.getLikes())
                .setText(R.id.tv_comment, comment.getComment())
                .addOnClickListener(R.id.iv_userhead)
                .setVisible(R.id.tv_spoiler_tip, comment.isSpoiler() ? true : false);

        User user = comment.getUser();
        String avatarUrl = MoCloudUtil.getUserAvatar(user);
        String username = MoCloudUtil.getUsername(user);
        ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
        ImageLoader.loadDontAnimate(mContext, avatarUrl, iv_userhead, R.drawable.default_userhead);

        TextView tvUsername = baseViewHolder.getView(R.id.tv_username);
        if (username.equals(commentOwner)){
            tvUsername.setTypeface(tvUsername.getTypeface(),Typeface.BOLD);
            tvUsername.setTextColor(ResUtil.getColor(R.color.comment_owner,null));
        }else{
            tvUsername.setTypeface(Typeface.DEFAULT);
            tvUsername.setTextColor(ResUtil.getColor(R.color.comment_user,null));
        }
        tvUsername.setText(username);
    }
}
