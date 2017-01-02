package com.floatingmuseum.mocloud.ui.login;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TraktToken;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;


/**
 * Created by Floatingmuseum on 2016/9/18.
 */
public class LoginPresenter extends Presenter implements DataCallback<TraktToken> {

    private LoginActivity loginActivity;

    public LoginPresenter(@NonNull LoginActivity loginActivity){
        this.loginActivity = loginActivity;
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

    @Override
    public void start(boolean shouldClean) {

    }
}
