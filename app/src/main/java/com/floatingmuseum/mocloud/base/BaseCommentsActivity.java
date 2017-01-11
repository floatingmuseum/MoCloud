package com.floatingmuseum.mocloud.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.ui.comments.SingleCommentActivity;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Floatingmuseum on 2017/1/11.
 */

public abstract class BaseCommentsActivity extends BaseActivity {

    protected TextView tvCommentReplies;

    protected void initCommentItem(final Context context, CardView comment_item, final Comment comment, boolean isSingleCommentActivity) {
        CircleImageView iv_userhead = (CircleImageView) comment_item.findViewById(R.id.iv_userhead);
        TextView tv_username = (TextView) comment_item.findViewById(R.id.tv_username);
        TextView tv_createtime = (TextView) comment_item.findViewById(R.id.tv_createtime);
        TextView tv_updatetime = (TextView) comment_item.findViewById(R.id.tv_updatetime);
        TextView tv_comments_likes = (TextView) comment_item.findViewById(R.id.tv_comment_likes);
        tvCommentReplies = (TextView) comment_item.findViewById(R.id.tv_comments_replies);
        TextView tv_comment = (TextView) comment_item.findViewById(R.id.tv_comment);
        LinearLayout ll_tip = (LinearLayout) comment_item.findViewById(R.id.ll_tip);
        TextView tv_spoiler_tip = (TextView) comment_item.findViewById(R.id.tv_spoiler_tip);
        TextView tv_review_tip = (TextView) comment_item.findViewById(R.id.tv_review_tip);

        String avatarUrl = MoCloudUtil.getUserAvatar(comment.getUser());
        ImageLoader.loadDontAnimate(this, avatarUrl, iv_userhead, R.drawable.default_userhead);

        String name = MoCloudUtil.getUsername(comment.getUser());
        tv_username.setText(name);

        tv_createtime.setText(TimeUtil.formatGmtTime(comment.getCreated_at()));
        tv_comments_likes.setText("" + comment.getLikes());
        tvCommentReplies.setText("" + comment.getReplies());
        tv_comment.setText(comment.getComment());

        tv_updatetime.setVisibility(comment.getCreated_at().equals(comment.getUpdated_at()) ? View.GONE : View.VISIBLE);
        tv_updatetime.setText("---updated at " + TimeUtil.formatGmtTime(comment.getUpdated_at()));

        if (comment.isSpoiler() || comment.isReview()) {
            tv_spoiler_tip.setVisibility(comment.isSpoiler() ? View.VISIBLE : View.GONE);
            tv_review_tip.setVisibility(comment.isReview() ? View.VISIBLE : View.GONE);
        } else {
            ll_tip.setVisibility(View.GONE);
        }

        tv_comments_likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("评论ID：" + comment.getId() + "...");
            }
        });

        iv_userhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(context, comment.getUser());
            }
        });

        if (isSingleCommentActivity) {
            tv_username.setTextColor(ResUtil.getColor(R.color.comment_owner, null));
            tv_username.setTypeface(null, Typeface.BOLD);
            return;
        }

        tv_comment.setMaxLines(5);
        tv_comment.setEllipsize(TextUtils.TruncateAt.END);
        comment_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleCommentActivity.class);
                intent.putExtra(SingleCommentActivity.MAIN_COMMENT, comment);
                startActivity(intent);
            }
        });
    }
}
