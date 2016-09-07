package com.floatingmuseum.mocloud.ui.comments;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
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
//        Image image = comment.getUser().getImages();
//        if (image != null && image.getAvatar() != null) {
//            ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
//            ImageLoader.load(mContext, comment.getUser().getImages().getAvatar().getFull(), iv_userhead, R.drawable.default_userhead);
//        }

        if (comment.isReview()) {
            baseViewHolder.setBackgroundColor(R.id.comment_title, ResUtil.getColor(R.color.comment_review_title_grey, null));
            baseViewHolder.setBackgroundColor(R.id.tv_comment, ResUtil.getColor(R.color.comment_review_content_grey, null));
        }

        String nickName = comment.getUser().getName();
        if (nickName == null || nickName.equals("")) {
            nickName = comment.getUser().getUsername();
            Logger.d("用户名："+nickName);
            baseViewHolder.setText(R.id.tv_username, comment.getUser().getUsername());
        } else {
            Logger.d("用户名："+nickName);
            baseViewHolder.setText(R.id.tv_username, nickName);
        }

        System.out.println("用户名："+nickName+"...评论："+comment.getComment()+"...评论长度："+comment.getComment().length());
//        Logger.d();

        baseViewHolder.setText(R.id.tv_updatetime, TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comments_likes, "" + comment.getLikes())
                .setText(R.id.tv_comment, comment.getComment())
                .setVisible(R.id.tv_spoiler_tip,comment.isSpoiler()?true:false);

        ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
        ImageLoader.load(mContext, comment.getUser().getImages().getAvatar().getFull(), iv_userhead, R.drawable.default_userhead);
    }
}
