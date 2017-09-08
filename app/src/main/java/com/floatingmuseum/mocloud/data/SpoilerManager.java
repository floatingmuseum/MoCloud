package com.floatingmuseum.mocloud.data;

import android.graphics.BlurMaskFilter;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.Pair;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.MaskFilterSpan;
import android.view.View;
import android.widget.TextView;

import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2017/9/8.
 */
// TODO: 2017/9/8 还可添加已经unBlur的剧透保存在本地,以后浏览不再覆盖模糊效果. 
public class SpoilerManager {

    private static SpoilerManager manager;
    private LongSparseArray<List<Pair<Integer, Integer>>> unBlurredContents = new LongSparseArray<>();

    private SpoilerManager() {
    }

    public static SpoilerManager getInstance() {
        if (manager == null) {
            synchronized (SpoilerManager.class) {
                if (manager == null) {
                    manager = new SpoilerManager();
                }
            }
        }
        return manager;
    }

    public void updateBlurSpan(long commentID, Pair<Integer, Integer> indexPair) {
        List<Pair<Integer, Integer>> pairList = unBlurredContents.get(commentID);
        if (pairList != null) {
            if (!pairList.contains(indexPair)) {
                pairList.add(indexPair);
            }
        } else {
            List<Pair<Integer, Integer>> newPairList = new ArrayList<>();
            newPairList.add(indexPair);
            unBlurredContents.put(commentID, newPairList);
        }
    }

    public void updateClickableSpan(final Comment comment, SpannableString maskComment, final TextView tvComment, final int commentColor, final Pair<Integer, Integer> pair) {
        maskComment.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(commentColor);
            }

            @Override
            public void onClick(View widget) {
                ToastUtil.showToast("你点击的Spoiler位置:" + pair.first + "..." + pair.second);
                updateBlurSpan(comment.getId(), pair);
                setBlurSpan(comment, tvComment, commentColor, true);
            }
        }, pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    public void setBlurSpan(final Comment comment, final TextView tvComment, final int commentColor, boolean clickable) {
        String rawComment = comment.getComment();
        SpannableString maskComment = new SpannableString(rawComment);

        if (comment.isSpoiler()) {
            if (!isSpoilerUnBlurred(comment.getId(), 0, maskComment.length())) {
                maskComment.setSpan(new MaskFilterSpan(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL)), 0, maskComment.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                if (clickable) {
                    updateClickableSpan(comment, maskComment, tvComment, commentColor, new Pair<>(0, maskComment.length()));
                }
            }
        } else if (comment.getComment().contains("[spoiler]") && comment.getComment().contains("[/spoiler]")) {
            List<Pair<Integer, Integer>> spoilersContainer = new ArrayList<>();
            getSpoilerIndex(comment.getId(), rawComment, 0, spoilersContainer);
            for (final Pair<Integer, Integer> pair : spoilersContainer) {
                maskComment.setSpan(new MaskFilterSpan(new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL)), pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                if (clickable) {
                    updateClickableSpan(comment, maskComment, tvComment, commentColor, new Pair<>(pair.first, pair.second));

//                    maskComment.setSpan(new ClickableSpan() {
//                        @Override
//                        public void updateDrawState(TextPaint ds) {
//                            super.updateDrawState(ds);
//                            ds.setUnderlineText(false);
//                            ds.setColor(commentColor);
//                        }
//
//                        @Override
//                        public void onClick(View widget) {
//                            ToastUtil.showToast("你点击的Spoiler位置:" + pair.first + "..." + pair.second);
//                            updateBlurSpan(comment.getId(), pair);
//                            setBlurSpan(comment, tvComment, commentColor, true);
//                        }
//                    }, pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                }

//                maskComment.setSpan(new BackgroundColorSpan(itemColors.getBodyTextColor()), pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                maskComment.setSpan(new StrikethroughSpan(), pair.first, pair.second, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                Logger.d("评论内容...剧透位置:" + pair.toString());
            }
        }
        tvComment.setText(maskComment);
//        return maskComment;
    }

    private void getSpoilerIndex(long commentID, String rawComment, int beginIndex, List<Pair<Integer, Integer>> spoilersContainer) {
        int startIndex = rawComment.indexOf("[spoiler]", beginIndex);
        int endIndex = rawComment.indexOf("[/spoiler]", beginIndex);
        if (startIndex != -1 && endIndex != -1) {
            endIndex += "[/spoiler]".length();
            if (!isSpoilerUnBlurred(commentID, startIndex, endIndex)) {
                spoilersContainer.add(new Pair<>(startIndex, endIndex));
            }
            getSpoilerIndex(commentID, rawComment, endIndex, spoilersContainer);
        }
    }

    private boolean isSpoilerUnBlurred(long commentID, int startIndex, int endIndex) {
        int key = unBlurredContents.indexOfKey(commentID);
        if (key != -1) {
            List<Pair<Integer, Integer>> blurIndexes = unBlurredContents.get(commentID);
            for (Pair<Integer, Integer> blurIndex : blurIndexes) {
                if (blurIndex.first == startIndex && blurIndex.second == endIndex) {
                    return true;
                }
            }
        }
        return false;
    }
}
