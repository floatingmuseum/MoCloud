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
import com.orhanobut.logger.Logger;

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
        if (comment.isReview()) {
            baseViewHolder.setBackgroundColor(R.id.comment_title, ResUtil.getColor(R.color.comment_review_title_grey, null));
            baseViewHolder.setBackgroundColor(R.id.tv_comment, ResUtil.getColor(R.color.comment_review_content_grey, null));
        }

        baseViewHolder.setText(R.id.tv_updatetime, TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comments_likes, "" + comment.getLikes())
                .setText(R.id.tv_comment, comment.getComment())
                .setVisible(R.id.tv_spoiler_tip, comment.isSpoiler() ? true : false);

        User user = comment.getUser();
        ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
        if (comment.getUser().isPrivateX()) {
            baseViewHolder.setText(R.id.tv_username, user.getUsername());
            ImageLoader.loadDontAnimate(mContext, "", iv_userhead, R.drawable.default_userhead);
        } else {
            Logger.d("Gif:"+"用户名:"+user.getUsername()+"..."+user.getImages().getAvatar().getFull());
            baseViewHolder.setText(R.id.tv_username, getUserName(user));
            ImageLoader.loadDontAnimate(mContext, user.getImages().getAvatar().getFull(), iv_userhead, R.drawable.default_userhead);
        }
    }

    private String getUserName(User user) {
        String name = user.getName();
        if (name != null && name.length() > 0) {
            return name;
        } else {
            return user.getUsername();
        }
    }
}
