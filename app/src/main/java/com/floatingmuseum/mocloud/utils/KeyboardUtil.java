package com.floatingmuseum.mocloud.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.floatingmuseum.mocloud.MoCloud;

/**
 * Created by Floatingmuseum on 2016/12/22.
 */

public class KeyboardUtil {

    public static void hideSoftInput(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showSoftInput(EditText edit) {
        edit.setFocusable(true);
        edit.setFocusableInTouchMode(true);
        edit.requestFocus();
        InputMethodManager imm = (InputMethodManager) MoCloud.context
                .getSystemService(MoCloud.context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, 0);
    }
}
