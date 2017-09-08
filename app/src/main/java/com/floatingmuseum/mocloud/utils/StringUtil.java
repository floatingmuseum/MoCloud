package com.floatingmuseum.mocloud.utils;

import android.graphics.BlurMaskFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.MaskFilterSpan;
import android.util.Pair;

import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/8/29.
 */
public class StringUtil {

    public static String removeBlank(String content) {
        return content.replaceAll(" ", "");
    }

    public static String buildImageUrl(String url, int type) {
        if (ImageCacheManager.TYPE_POSTER == type) {
//            return "https://image.tmdb.org/t/p/w92" + url;
//            return "https://image.tmdb.org/t/p/154" + url;
//            return "https://image.tmdb.org/t/p/w185" + url;
//            return "https://image.tmdb.org/t/p/w342" + url;
            return "https://image.tmdb.org/t/p/w500" + url;
//            return "https://image.tmdb.org/t/p/w780" + url;
//            return "https://image.tmdb.org/t/p/original" + url;
        } else {
//            return "https://image.tmdb.org/t/p/w300" + url;
            return "https://image.tmdb.org/t/p/w780" + url;
//            return "https://image.tmdb.org/t/p/w1280" + url;
//            return "https://image.tmdb.org/t/p/original" + url;
        }
    }

    public static String buildPeopleHeadshotUrl(String url) {
//        return "https://image.tmdb.org/t/p/w45" + url;
        return "https://image.tmdb.org/t/p/w185" + url;
//        return "https://image.tmdb.org/t/p/h632" + url;
//        return "https://image.tmdb.org/t/p/original" + url;
    }

    /**
     * 获取文件后缀名
     */
    public static String getFileSuffix(String content) {
        return content.substring(content.lastIndexOf("."));
    }

    public static boolean checkReplyContent(String content) {
        String[] contentArray = content.split(" ");
        if (contentArray.length < 5) {
            return false;
        }
        int englishNum = 0;
        for (String s : contentArray) {
            if (isContainLetter(s)) {
                englishNum++;
            }
            if (englishNum > 5) {
                return true;
            }
        }
        return false;
    }

    public static boolean isContainLetter(String content) {
        for (char c : content.toCharArray()) {
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasData(String tagline) {
        if (tagline != null && tagline.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static SpannableString getBlurSpan(Comment comment){
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

    public static void getSpoilerIndex(String rawComment, int beginIndex, List<Pair<Integer, Integer>> spoilersContainer) {
        int startIndex = rawComment.indexOf("[spoiler]", beginIndex);
        int endIndex = rawComment.indexOf("[/spoiler]", beginIndex);
        if (startIndex != -1 && endIndex != -1) {
            spoilersContainer.add(new Pair<>(startIndex, endIndex + "[/spoiler]".length()));
            getSpoilerIndex(rawComment, endIndex + "[/spoiler]".length(), spoilersContainer);
        }
    }
}
