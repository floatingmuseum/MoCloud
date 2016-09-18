package com.floatingmuseum.mocloud.ui.login;

import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.SharedPreferencesCompat;

import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
public class LoginPresenter implements DataCallback<TraktToken> {

    LoginActivity loginActivity;
    Repository repository;

    @Inject
    public LoginPresenter(@NonNull LoginActivity loginActivity, @NonNull Repository repository){
        this.loginActivity = loginActivity;
        this.repository = repository;
    }

    public void start(String code){
        repository.getAccessToken(code,this);
    }

    @Override
    public void onBaseDataSuccess(TraktToken traktToken) {
        Logger.d("access_token:"+traktToken.getAccess_token());
        Logger.d("refresh_token:"+traktToken.getRefresh_token());
        Logger.d("expires_in:"+traktToken.getExpires_in());
        SPUtil.saveToken(traktToken);
        loginActivity.requestTokenSuccess();
    }

    @Override
    public void onError(Throwable e) {
        loginActivity.requestTokenFailed();
    }
}
