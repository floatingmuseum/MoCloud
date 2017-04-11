package com.floatingmuseum.mocloud.ui;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by Floatingmuseum on 2017/4/11.
 *
 * 计划用来在发送评论和点击观看，想看等按钮时，弹出Loading dialog
 */

public class LoadingDialog extends AlertDialog{

    protected LoadingDialog(Context context) {
        super(context);
    }

    protected LoadingDialog(Context context, int theme) {
        super(context, theme);
    }

    protected LoadingDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
