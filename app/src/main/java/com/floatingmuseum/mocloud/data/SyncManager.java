package com.floatingmuseum.mocloud.data;


import com.floatingmuseum.mocloud.utils.SPUtil;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class SyncManager{

    public static void startSync(){
        SPUtil.getBoolean(SPUtil.SP_USER_LASTACTIVITIES,"has_first_sync",false);
    }
}
