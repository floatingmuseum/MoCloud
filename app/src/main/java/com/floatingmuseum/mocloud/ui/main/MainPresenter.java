package com.floatingmuseum.mocloud.ui.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.SyncService;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.utils.ErrorUtil;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

import rx.Subscription;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
class MainPresenter extends Presenter implements DataCallback<UserSettings> {

    private MainActivity1 mainActivity;


    MainPresenter(@NonNull MainActivity1 mainActivity) {
        this.mainActivity = mainActivity;
    }

    void syncUserData(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        context.startService(intent);
    }

    @Override
    public void onBaseDataSuccess(UserSettings userSettings) {

    }

    @Override
    public void onError(Throwable e) {
        if (ErrorUtil.is401Error(e)) {
            Logger.d("you need login");
        }
        e.printStackTrace();
    }

    public void logout() {
        SPUtil.removeUserSettings();
        SPUtil.editBoolean("isLogin", false);
    }
}
