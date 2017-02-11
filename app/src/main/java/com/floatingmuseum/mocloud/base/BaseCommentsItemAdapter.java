package com.floatingmuseum.mocloud.base;

import android.graphics.Typeface;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Colors;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.utils.ColorUtil;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/11.
 */

public class BaseCommentsItemAdapter extends BaseQuickAdapter<Comment, BaseViewHolder> {

    private String commentOwner;
    private Colors itemColors;

    public BaseCommentsItemAdapter(List<Comment> commentsData, Colors itemColors) {
        super(R.layout.comment_item, commentsData);
        this.itemColors = itemColors;
    }

    public BaseCommentsItemAdapter(List<Comment> commentsData, String commentOwner, Colors itemColors) {
        super(R.layout.comment_item, commentsData);
        this.commentOwner = commentOwner;
        this.itemColors = itemColors;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
        User user = comment.getUser();
        String username = MoCloudUtil.getUsername(user);
        Integer userRating = comment.getUser_rating();
        baseViewHolder.setText(R.id.tv_createtime, TimeUtil.formatGmtTime(comment.getCreated_at()))
                .setText(R.id.tv_updatetime, "---updated at " + TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comment_likes, "" + comment.getLikes())
                .setText(R.id.tv_comment, comment.getComment())
                .setText(R.id.tv_username, username)
                .setText(R.id.tv_rating_tip, userRating + "/10")
                .addOnClickListener(R.id.iv_userhead)
                .setVisible(R.id.tv_spoiler_tip, comment.isSpoiler() ? true : false)
                .setVisible(R.id.tv_review_tip, comment.isReview() ? true : false)
                .setVisible(R.id.tv_rating_tip, userRating == null ? false : true)
                .setVisible(R.id.ll_tip, comment.isSpoiler() || comment.isReview() || userRating != null ? true : false)
                .setVisible(R.id.tv_updatetime, comment.getCreated_at().equals(comment.getUpdated_at()) ? false : true);
        ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
        ImageLoader.loadDontAnimate(mContext, MoCloudUtil.getUserAvatar(user), iv_userhead, R.drawable.default_userhead);
        TextView tvUsername = baseViewHolder.getView(R.id.tv_username);
        initColors(baseViewHolder);
        if (commentOwner == null) {
            return;
        }

        if (username.equals(commentOwner)) {
            tvUsername.setTypeface(tvUsername.getTypeface(), Typeface.BOLD);
        } else {
            tvUsername.setTypeface(Typeface.DEFAULT);
        }
    }

    private void initColors(BaseViewHolder holder) {
        if (itemColors != null) {
            holder.setBackgroundColor(R.id.comment_title, ColorUtil.darkerColor(itemColors.getRgb(), 0.1))
                    .setBackgroundColor(R.id.tv_comment, ColorUtil.darkerColor(itemColors.getRgb(), 0.2))
                    .setBackgroundColor(R.id.tv_updatetime, ColorUtil.darkerColor(itemColors.getRgb(), 0.2))
                    .setTextColor(R.id.tv_username,itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_createtime, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_updatetime, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_comments_replies, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_comment_likes, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_comment, itemColors.getBodyTextColor());
        }
    }
}
