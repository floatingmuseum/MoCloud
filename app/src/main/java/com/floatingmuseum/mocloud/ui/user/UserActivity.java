package com.floatingmuseum.mocloud.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseActivity;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by Floatingmuseum on 2016/9/14.
 */
public class UserActivity extends BaseActivity {

    @Override
    protected int currentLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d("username:"+SPUtil.getString(SPUtil.SP_USER_SETTINGS,"username","-1"));
    }

    @Override
    protected void initView() {

    }
}
