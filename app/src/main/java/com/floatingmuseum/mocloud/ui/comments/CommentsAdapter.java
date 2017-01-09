package com.floatingmuseum.mocloud.ui.comments;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsAdapter extends BaseQuickAdapter<Comment> {
    public CommentsAdapter(List<Comment> data) {
        super(R.layout.comment_item, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
        // TODO: 2017/1/9 blur spoiler comment 
        if (comment.isReview()) {
            baseViewHolder.setBackgroundColor(R.id.comment_title, ResUtil.getColor(R.color.comment_review_title_grey, null));
            baseViewHolder.setBackgroundColor(R.id.tv_comment, ResUtil.getColor(R.color.comment_review_content_grey, null));
        }
        User user = comment.getUser();

        baseViewHolder.setText(R.id.tv_updatetime, TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comment_likes, "" + comment.getLikes())
                .setText(R.id.tv_comment, comment.getComment())
                .setText(R.id.tv_username, MoCloudUtil.getUsername(user))
                .addOnClickListener(R.id.iv_userhead)
                .setVisible(R.id.tv_spoiler_tip, comment.isSpoiler() ? true : false);
        ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
        ImageLoader.loadDontAnimate(mContext, MoCloudUtil.getUserAvatar(user), iv_userhead, R.drawable.default_userhead);
    }
}
