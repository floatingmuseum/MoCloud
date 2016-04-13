package com.floatingmuseum.mocloud.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;

import com.floatingmuseum.mocloud.MoCloud;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class ResUtil {
    private ResUtil(){}

    public static String getString(int resId){
        return MoCloud.context.getResources().getString(resId);
    }

    public static Drawable getDrawable(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return MoCloud.context.getResources().getDrawable(resId, null);
        }else{
            return MoCloud.context.getResources().getDrawable(resId);
        }
    }

    public static String[] getStringArray(int resId){
        return MoCloud.context.getResources().getStringArray(resId);
    }
}
