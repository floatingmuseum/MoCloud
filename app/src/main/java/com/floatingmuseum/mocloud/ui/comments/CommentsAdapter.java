package com.floatingmuseum.mocloud.ui.comments;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/9/1.
 */
public class CommentsAdapter extends BaseQuickAdapter<Comment> {
    public CommentsAdapter(List<Comment> data) {
        super(R.layout.comment_item,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Comment comment) {
        Image image = comment.getUser().getImages();
        if(image!=null && image.getAvatar()!=null){
            String userHeadUrl = StringUtil.removeBlank(image.getAvatar().getFull());
            ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
            ImageLoader.load(mContext,userHeadUrl,iv_userhead,R.drawable.default_userhead);
        }

        if(comment.isReview()){
            baseViewHolder.getView(R.id.tv_comment).setBackgroundColor(ResUtil.getColor(R.color.comment_review_content_grey,null));
            baseViewHolder.getView(R.id.comment_title).setBackgroundColor(ResUtil.getColor(R.color.comment_review_title_grey,null));
        }

        baseViewHolder.setText(R.id.tv_username,comment.getUser().getName());
        baseViewHolder.setText(R.id.tv_updatetime, TimeUtil.formatGmtTime(comment.getUpdated_at()));
        baseViewHolder.setText(R.id.tv_comments_replies,""+comment.getReplies());
        baseViewHolder.setText(R.id.tv_comments_likes,""+comment.getLikes());
        baseViewHolder.setText(R.id.tv_comment,comment.getComment());
    }
}
