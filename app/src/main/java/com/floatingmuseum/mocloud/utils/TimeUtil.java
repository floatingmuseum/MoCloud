package com.floatingmuseum.mocloud.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Floatingmuseum on 2016/8/18.
 */
public class TimeUtil {

    public static final String GMT_ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String TIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT2 = "yyyy-MM-dd";

    // TODO: 2016/9/1 need more test
    public static String formatGmtTime(String gmtTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(GMT_ISO8601_FORMAT);
        Date date;
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            date = dateFormat.parse(gmtTime);
            dateFormat.applyPattern(TIME_FORMAT1);
            return new SimpleDateFormat(TIME_FORMAT1).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date formatStringToDate(String time,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
