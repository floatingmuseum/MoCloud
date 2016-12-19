package com.floatingmuseum.mocloud.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;

import com.floatingmuseum.mocloud.MoCloud;

/**
 * Created by Floatingmuseum on 2016/12/19.
 */

public class PermissionsUtil {

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean hasPermission(String permission) {
        int hasIt = MoCloud.context.checkSelfPermission(permission);
        if (hasIt == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}
