package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.ui.login.LoginActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
@Module
public class LoginPresenterModule {
    private LoginActivity loginActivity;

    public LoginPresenterModule(LoginActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    @Provides
    LoginActivity providesLoginActivity(){return loginActivity;}
}
