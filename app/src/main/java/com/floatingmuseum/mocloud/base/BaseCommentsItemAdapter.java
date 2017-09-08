package com.floatingmuseum.mocloud.base;

import android.graphics.BlurMaskFilter;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.MaskFilterSpan;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.SpoilerManager;
import com.floatingmuseum.mocloud.data.entity.Colors;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.utils.ColorUtil;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.MoCloudUtil;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
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
        Logger.d("是否剧透:" + comment.isSpoiler() + "...评论内容:" + comment.getComment());
//        SpannableString finalComment = maskSpoilerComment(comment);
//        SpannableString finalComment = StringUtil.getBlurSpan(comment);
        SpoilerManager.getInstance().setBlurSpan(comment, (TextView) baseViewHolder.getView(R.id.tv_comment), itemColors.getBodyTextColor(), false);
        baseViewHolder.setText(R.id.tv_createtime, TimeUtil.formatGmtTime(comment.getCreated_at()))
                .setText(R.id.tv_updatetime, "---updated at " + TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comment_likes, "" + comment.getLikes())
//                .setText(R.id.tv_comment, finalComment)
                .setText(R.id.tv_username, username)
                .setText(R.id.tv_rating_tip, userRating + "/10")
                .setImageDrawable(R.id.iv_comment_likes, comment.isLike() ? ResUtil.getDrawable(R.drawable.ic_thumb_up_fill_blue_48dp) : ResUtil.getDrawable(R.drawable.ic_thumb_up_stroke_blue_48dp))
                .addOnClickListener(R.id.iv_userhead)
                .addOnClickListener(R.id.ll_comment_likes)
                .setVisible(R.id.iv_replies, commentOwner == null)
                .setVisible(R.id.tv_comments_replies, commentOwner == null)
                .setVisible(R.id.tv_spoiler_tip, comment.isSpoiler())
                .setVisible(R.id.tv_review_tip, comment.isReview())
                .setVisible(R.id.tv_rating_tip, userRating != null)
                .setVisible(R.id.ll_tip, comment.isSpoiler() || comment.isReview() || userRating != null)
                .setVisible(R.id.tv_updatetime, !comment.getCreated_at().equals(comment.getUpdated_at()));
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

    private SpannableString maskSpoilerComment(Comment comment) {
        String rawComment = comment.getComment();
        SpannableString maskComment = new SpannableString(rawComment);

        if (comment.isSpoiler()) {
            maskComment.setSpan(new MaskFilterSpan(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL)), 0, maskComment.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        } else if (comment.getComment().contains("[spoiler]") && comment.getComment().contains("[/spoiler]")) {
            List<Pair<Integer, Integer>> spoilersContainer = new ArrayList<>();
            getSpoilerIndex(rawComment, 0, spoilersContainer);
            for (Pair<Integer, Integer> pair : spoilersContainer) {
                maskComment.setSpan(new MaskFilterSpan(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL)), pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                maskComment.setSpan(new BackgroundColorSpan(itemColors.getBodyTextColor()), pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                maskComment.setSpan(new StrikethroughSpan(), pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                Logger.d("评论内容...剧透位置:" + pair.toString());
            }
        }
        return maskComment;
    }

    private void getSpoilerIndex(String rawComment, int beginIndex, List<Pair<Integer, Integer>> spoilersContainer) {
        int startIndex = rawComment.indexOf("[spoiler]", beginIndex);
        int endIndex = rawComment.indexOf("[/spoiler]", beginIndex);
        if (startIndex != -1 && endIndex != -1) {
            spoilersContainer.add(new Pair<>(startIndex, endIndex + "[/spoiler]".length()));
            getSpoilerIndex(rawComment, endIndex + "[/spoiler]".length(), spoilersContainer);
        }
    }


    private void initColors(BaseViewHolder holder) {
        if (itemColors != null) {
            holder.setBackgroundColor(R.id.comment_title, ColorUtil.darkerColor(itemColors.getRgb(), 0.1))
                    .setBackgroundColor(R.id.tv_comment, ColorUtil.darkerColor(itemColors.getRgb(), 0.2))
                    .setBackgroundColor(R.id.tv_updatetime, ColorUtil.darkerColor(itemColors.getRgb(), 0.2))
                    .setTextColor(R.id.tv_username, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_createtime, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_updatetime, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_comments_replies, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_comment_likes, itemColors.getTitleTextColor())
                    .setTextColor(R.id.tv_comment, itemColors.getBodyTextColor());
            Logger.d("评论颜色:...2:" + itemColors.getBodyTextColor());
        }
    }
}
