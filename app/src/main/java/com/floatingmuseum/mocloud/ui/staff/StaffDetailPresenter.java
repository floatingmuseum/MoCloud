package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class StaffDetailPresenter implements DataCallback<TmdbStaff> {
    private StaffDetailActivity activity;
    private Repository repository;

    public StaffDetailPresenter(StaffDetailActivity activity, Repository repository) {
        this.activity = activity;
        this.repository = repository;
    }

    public void getData(int tmdbID) {
        repository.getStaffDetail(tmdbID,this);
    }

    @Override
    public void onBaseDataSuccess(TmdbStaff staff) {
        activity.onBaseDataSuccess(staff);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
