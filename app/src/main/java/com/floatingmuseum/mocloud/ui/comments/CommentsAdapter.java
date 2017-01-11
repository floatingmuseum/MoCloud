package com.floatingmuseum.mocloud.ui.comments;

import android.graphics.BlurMaskFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
        // TODO: 2017/1/9 blur spoiler comment,这里有个问题，部分review回复在blur之后会显示一片白
        User user = comment.getUser();

        baseViewHolder.setText(R.id.tv_createtime, TimeUtil.formatGmtTime(comment.getCreated_at()))
                .setText(R.id.tv_updatetime, "---updated at " + TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comment_likes, "" + comment.getLikes())
                .setText(R.id.tv_comment, comment.getComment())
                .setText(R.id.tv_username, MoCloudUtil.getUsername(user))
                .addOnClickListener(R.id.iv_userhead)
                .setVisible(R.id.tv_spoiler_tip, comment.isSpoiler() ? true : false)
                .setVisible(R.id.tv_review_tip, comment.isReview() ? true : false)
                .setVisible(R.id.ll_tip, comment.isSpoiler() || comment.isReview() ? true : false)
                .setVisible(R.id.tv_updatetime, comment.getCreated_at().equals(comment.getUpdated_at()) ? false : true);
        ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
        ImageLoader.loadDontAnimate(mContext, MoCloudUtil.getUserAvatar(user), iv_userhead, R.drawable.default_userhead);
//        TextView tv_comment = baseViewHolder.getView(R.id.tv_comment);
//        if (comment.isSpoiler()) {
//            float radius = tv_comment.getTextSize() / 20;
//            tv_comment.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            BlurMaskFilter blurMaskFilter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
//            tv_comment.getPaint().setMaskFilter(blurMaskFilter);
//        } else {
//            tv_comment.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//            tv_comment.getPaint().setMaskFilter(null);
//        }
    }
}
