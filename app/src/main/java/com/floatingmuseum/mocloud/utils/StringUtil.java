package com.floatingmuseum.mocloud.utils;

/**
 * Created by Floatingmuseum on 2016/8/29.
 */
public class StringUtil {

    public static String removeBlank(String content){
        return content.replaceAll(" ","");
    }

    public static String buildPosterUrl(String url){
        return "https://image.tmdb.org/t/p/w185"+url;
    }

    public static String buildPeopleHeadshotUrl(String url){
        return "https://image.tmdb.org/t/p/w185"+url;
    }

    /**
     * 获取文件后缀名
     */
    public static String getFileSuffix(String content){
        return content.substring(content.lastIndexOf("."));
    }
}
