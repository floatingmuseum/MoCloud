package com.floatingmuseum.mocloud.utils;

import com.orhanobut.logger.Logger;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Floatingmuseum on 2016/8/18.
 */
public class TimeUtil {

    private static final String GMT_ISO8601_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.mmm'Z'";
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // TODO: 2016/9/1 need more test
    public static String formatGmtTime(String gmtTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(GMT_ISO8601_FORMAT);
        Date date;
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            date = dateFormat.parse(gmtTime);
            dateFormat.applyPattern(TIME_FORMAT);
            return new SimpleDateFormat(TIME_FORMAT).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
