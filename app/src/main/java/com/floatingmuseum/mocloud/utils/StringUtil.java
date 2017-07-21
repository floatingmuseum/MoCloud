package com.floatingmuseum.mocloud.utils;

import com.floatingmuseum.mocloud.data.net.ImageCacheManager;

/**
 * Created by Floatingmuseum on 2016/8/29.
 */
public class StringUtil {

    public static String removeBlank(String content) {
        return content.replaceAll(" ", "");
    }

    public static String buildImageUrl(String url, int type) {
        if (ImageCacheManager.TYPE_POSTER==type){
            return "https://image.tmdb.org/t/p/w500" + url;
        }else{
            return "https://image.tmdb.org/t/p/w780" + url;
        }
    }

    public static String buildPeopleHeadshotUrl(String url) {
        return "https://image.tmdb.org/t/p/w632" + url;
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
}
