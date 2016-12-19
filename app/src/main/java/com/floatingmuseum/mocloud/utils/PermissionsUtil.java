package com.floatingmuseum.mocloud.utils;

import android.content.pm.PackageManager;
import com.floatingmuseum.mocloud.MoCloud;

/**
 * Created by Floatingmuseum on 2016/12/19.
 */

public class PermissionsUtil {

    public static boolean hasPermission(String permission) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasIt = MoCloud.context.checkSelfPermission(permission);
            if (hasIt == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        }else{
            return true;
        }
    }
}
