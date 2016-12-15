package com.floatingmuseum.mocloud.ui.login;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;


/**
 * Created by Floatingmuseum on 2016/9/18.
 */
public class LoginPresenter implements DataCallback<TraktToken> {

    LoginActivity loginActivity;
    Repository repository;

    public LoginPresenter(@NonNull LoginActivity loginActivity, @NonNull Repository repository){
        this.loginActivity = loginActivity;
        this.repository = repository;
    }

    public void exchangeAccessToken(String code){
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
        e.printStackTrace();
        loginActivity.requestTokenFailed();
    }
}
