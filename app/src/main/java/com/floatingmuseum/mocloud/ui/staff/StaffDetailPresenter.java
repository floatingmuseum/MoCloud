package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.StaffImages;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;

import rx.Subscription;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class StaffDetailPresenter extends Presenter implements DataCallback<StaffImages> {
    private StaffDetailActivity activity;

    public StaffDetailPresenter(StaffDetailActivity activity) {
        this.activity = activity;
    }

    public void getStaffImages(int tmdbId) {
        compositeSubscription.add(repository.getStaffImages(tmdbId,this));
    }

    @Override
    public void onBaseDataSuccess(StaffImages staffImages) {
        activity.onBaseDataSuccess(staffImages);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
