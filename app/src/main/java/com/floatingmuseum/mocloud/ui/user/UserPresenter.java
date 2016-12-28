package com.floatingmuseum.mocloud.ui.user;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;

/**
 * Created by Floatingmuseum on 2016/12/28.
 */

public class UserPresenter extends Presenter {
    private UserActivity activity;

    public UserPresenter(UserActivity activity){
        this.activity = activity;
    }
}
