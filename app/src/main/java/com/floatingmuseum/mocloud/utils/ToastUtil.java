package com.floatingmuseum.mocloud.utils;

import android.widget.Toast;

import com.floatingmuseum.mocloud.MoCloud;

/**
 * Created by FloatingMuseum on 2015/11/30.
 */
public class ToastUtil {
    private static Toast mToast = new Toast(MoCloud.context);

    private ToastUtil(){}

    /**
     * 传字符串
     * @param content
     */
    public static void showToast(String content) {
        mToast.makeText(MoCloud.context,content, Toast.LENGTH_SHORT).show();
    }

    /**
     * 传String资源id
     * @param resId
     */
    public static void showToast(int resId){
        mToast.makeText(MoCloud.context,resId, Toast.LENGTH_SHORT).show();
    }
}
