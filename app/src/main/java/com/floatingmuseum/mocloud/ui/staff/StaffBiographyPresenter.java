package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffBiographyPresenter extends Presenter implements DataCallback<TmdbStaff> {

    private StaffBiographyFragment fragment;

    public StaffBiographyPresenter(StaffBiographyFragment fragment){
        this.fragment = fragment;
    }

    public void start(int staffId){
        repository.getStaffDetail(staffId,this);
    }

    @Override
    public void onBaseDataSuccess(TmdbStaff staff) {
        fragment.onBaseDataSuccess(staff);
    }

    @Override
    public void onError(Throwable e) {

    }
}
