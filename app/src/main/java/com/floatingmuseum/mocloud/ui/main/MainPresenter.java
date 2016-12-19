package com.floatingmuseum.mocloud.ui.main;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
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
public class MainPresenter extends Presenter implements DataCallback<UserSettings> {

    MainActivity mainActivity;
    Repository repository;


    public MainPresenter(@NonNull MainActivity mainActivity, @NonNull Repository repository) {
        this.mainActivity = mainActivity;
        this.repository = repository;
    }

    public void getUserSettings() {
        Subscription userSettingsSubscription = repository.getUserSettings(this);
        compositeSubscription.add(userSettingsSubscription);
    }

    @Override
    public void onBaseDataSuccess(UserSettings userSettings) {
        //界面上暂时只需更新用户头像和用户名，然后存储UserSettings
        Logger.d("UserSettings:"+userSettings.getUser().getUsername()+"...");
        SPUtil.saveUserSettings(userSettings);
        mainActivity.refreshUserView(userSettings);
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
        mainActivity.refreshUserView(null);
    }
}
