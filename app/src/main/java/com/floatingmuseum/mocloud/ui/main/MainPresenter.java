package com.floatingmuseum.mocloud.ui.main;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
import com.floatingmuseum.mocloud.utils.SPUtil;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
public class MainPresenter implements DataCallback<UserSettings> {

    MainActivity mainActivity;
    Repository repository;

    @Inject
    public MainPresenter(@NonNull MainActivity mainActivity, @NonNull Repository repository){
        this.mainActivity = mainActivity;
        this.repository = repository;
    }

    public void getUserSettings() {
        repository.getUserSettings(SPUtil.getAccessToken(),this);
    }

    @Override
    public void onBaseDataSuccess(UserSettings userSettings) {
        //界面上暂时只需更新用户头像和用户名，然后存储UserSettings

    }

    @Override
    public void onError(Throwable e) {

    }
}
