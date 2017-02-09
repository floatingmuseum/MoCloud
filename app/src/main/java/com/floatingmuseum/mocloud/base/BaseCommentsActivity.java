package com.floatingmuseum.mocloud.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Colors;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.ui.comments.SingleCommentActivity;
import com.floatingmuseum.mocloud.utils.ColorUtil;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.KeyboardUtil;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Floatingmuseum on 2017/1/11.
 */

public abstract class BaseCommentsActivity extends BaseActivity {
    public static final String MAIN_COMMENT = "main_comment";
    public static final String MAIN_COLORS = "main_colors";
    public static final String ITEM_COLORS = "item_colors";
    protected TextView tvCommentReplies;

    protected void initCommentItem(final Context context, final CardView commentItem, final Comment comment, final Palette.Swatch mainSwatch, final Palette.Swatch itemSwatch, boolean isSingleCommentActivity) {
        CircleImageView ivUserhead = (CircleImageView) commentItem.findViewById(R.id.iv_userhead);
        TextView tvUsername = (TextView) commentItem.findViewById(R.id.tv_username);
        TextView tvCreatetime = (TextView) commentItem.findViewById(R.id.tv_createtime);
        TextView tvUpdatetime = (TextView) commentItem.findViewById(R.id.tv_updatetime);
        TextView tvCommentsLikes = (TextView) commentItem.findViewById(R.id.tv_comment_likes);
        tvCommentReplies = (TextView) commentItem.findViewById(R.id.tv_comments_replies);
        TextView tvComment = (TextView) commentItem.findViewById(R.id.tv_comment);
        LinearLayout llTip = (LinearLayout) commentItem.findViewById(R.id.ll_tip);
        TextView tvSpoilerTip = (TextView) commentItem.findViewById(R.id.tv_spoiler_tip);
        TextView tvReviewTip = (TextView) commentItem.findViewById(R.id.tv_review_tip);
        TextView tvRatingTip = (TextView) commentItem.findViewById(R.id.tv_rating_tip);

        LinearLayout commentTitle = (LinearLayout) commentItem.findViewById(R.id.comment_title);

        if (itemSwatch != null) {
            commentTitle.setBackgroundColor(ColorUtil.darkerColor(itemSwatch.getRgb(), 0.1));
            tvComment.setBackgroundColor(ColorUtil.darkerColor(itemSwatch.getRgb(), 0.2));
            tvUpdatetime.setBackgroundColor(ColorUtil.darkerColor(itemSwatch.getRgb(), 0.2));

            tvUsername.setTextColor(itemSwatch.getTitleTextColor());
            tvCreatetime.setTextColor(itemSwatch.getTitleTextColor());
            tvUpdatetime.setTextColor(itemSwatch.getTitleTextColor());
            tvCommentsLikes.setTextColor(itemSwatch.getTitleTextColor());
            tvCommentReplies.setTextColor(itemSwatch.getTitleTextColor());

            tvComment.setTextColor(itemSwatch.getBodyTextColor());
        }

        String avatarUrl = MoCloudUtil.getUserAvatar(comment.getUser());
        ImageLoader.loadDontAnimate(this, avatarUrl, ivUserhead, R.drawable.default_userhead);

        String name = MoCloudUtil.getUsername(comment.getUser());
        tvUsername.setText(name);

        tvCreatetime.setText(TimeUtil.formatGmtTime(comment.getCreated_at()));
        tvCommentsLikes.setText("" + comment.getLikes());
        tvCommentReplies.setText("" + comment.getReplies());
        tvComment.setText(comment.getComment());

        tvUpdatetime.setVisibility(comment.getCreated_at().equals(comment.getUpdated_at()) ? View.GONE : View.VISIBLE);
        tvUpdatetime.setText("---updated at " + TimeUtil.formatGmtTime(comment.getUpdated_at()));
        Integer userRating = comment.getUser_rating();
        if (comment.isSpoiler() || comment.isReview() || userRating != null) {
            tvSpoilerTip.setVisibility(comment.isSpoiler() ? View.VISIBLE : View.GONE);
            tvReviewTip.setVisibility(comment.isReview() ? View.VISIBLE : View.GONE);
            if (userRating != null) {
                tvRatingTip.setVisibility(View.VISIBLE);
                tvRatingTip.setText(userRating + "/10");
            } else {
                tvRatingTip.setVisibility(View.GONE);
            }
        } else {
            llTip.setVisibility(View.GONE);
        }

        tvCommentsLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d("评论ID：" + comment.getId() + "...");
            }
        });

        ivUserhead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUserActivity(context, comment.getUser());
            }
        });
        Logger.d("initCommentItem4");

        if (isSingleCommentActivity) {
            tvUsername.setTextColor(ResUtil.getColor(R.color.comment_owner, null));
            tvUsername.setTypeface(null, Typeface.BOLD);
            return;
        }

        tvComment.setTextIsSelectable(false);//点击事件冲突，想复制文字的话去详情里面复制吧
        tvComment.setMaxLines(5);
        tvComment.setEllipsize(TextUtils.TruncateAt.END);
        commentItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleCommentActivity.class);
                if (itemSwatch != null) {
                    Colors mainColors = buildColors(mainSwatch);
                    Colors itemColors = buildColors(itemSwatch);
                    intent.putExtra(MAIN_COLORS, mainColors);
                    intent.putExtra(ITEM_COLORS, itemColors);
                }

                intent.putExtra(SingleCommentActivity.MAIN_COMMENT, comment);
                startActivity(intent);
            }
        });
    }

    protected Colors buildColors(Palette.Swatch swatch) {
        Colors colors = new Colors();
        colors.setRgb(swatch.getRgb());
        colors.setHsl(swatch.getHsl());
        colors.setPopulation(swatch.getPopulation());
        colors.setTitleTextColor(swatch.getBodyTextColor());
        colors.setBodyTextColor(swatch.getTitleTextColor());
        return colors;
    }

    protected void resetCommentBox(EditText commentBox, CheckBox isSpoiler) {
        commentBox.setText("");
        isSpoiler.setChecked(false);
        KeyboardUtil.hideSoftInput(this);
    }
}
