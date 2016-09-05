package com.floatingmuseum.mocloud.ui.comments;

import android.graphics.BlurMaskFilter;
import android.renderscript.RenderScript;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.MaskFilterSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.Image;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.utils.TimeUtil;
import com.orhanobut.logger.Logger;

import org.w3c.dom.Text;

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
        Image image = comment.getUser().getImages();
        if (image != null && image.getAvatar() != null) {
            String userHeadUrl = StringUtil.removeBlank(image.getAvatar().getFull());
            ImageView iv_userhead = baseViewHolder.getView(R.id.iv_userhead);
            ImageLoader.load(mContext, userHeadUrl, iv_userhead, R.drawable.default_userhead);
        }

        if (comment.isReview()) {
            baseViewHolder.setBackgroundColor(R.id.comment_title,ResUtil.getColor(R.color.comment_review_title_grey, null));
            baseViewHolder.setBackgroundColor(R.id.tv_comment,ResUtil.getColor(R.color.comment_review_content_grey, null));
        }

        String nickName = comment.getUser().getName();
        if (nickName == null || nickName.equals("")) {
            baseViewHolder.setText(R.id.tv_username, comment.getUser().getUsername());
        } else {
            baseViewHolder.setText(R.id.tv_username, nickName);
        }

//        if(comment.isSpoiler()){
//            baseViewHolder.setText(R.id.tv_comment,comment.getComment()).addOnClickListener(R.id.fl_comment);
////                    .addOnClickListener(R.id.tv_comment)
////                    .addOnClickListener(R.id.tv_spoiler);
//            TextView tv_spoiler = baseViewHolder.getView(R.id.tv_spoiler);
//            TextView tv_comment = baseViewHolder.getView(R.id.tv_comment);
//            tv_spoiler.setVisibility(View.GONE);
//
//            tv_comment.setVisibility(View.VISIBLE);
//            tv_comment.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//            float radius = tv_comment.getTextSize() / 3;
//            Logger.d("用户名："+nickName+"剧透倾向："+comment.getComment());
//            SpannableString spannableString = new SpannableString(comment.getComment());
//            MaskFilterSpan maskFilterSpan = new MaskFilterSpan(new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL));
//            spannableString.setSpan(maskFilterSpan,0,spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            tv_comment.setText(spannableString);
//        }else{
            baseViewHolder.setText(R.id.tv_comment, comment.getComment());
//        }

        baseViewHolder.setText(R.id.tv_updatetime, TimeUtil.formatGmtTime(comment.getUpdated_at()))
                .setText(R.id.tv_comments_replies, "" + comment.getReplies())
                .setText(R.id.tv_comments_likes, "" + comment.getLikes());
//        baseViewHolder;
//        baseViewHolder;
    }
}
