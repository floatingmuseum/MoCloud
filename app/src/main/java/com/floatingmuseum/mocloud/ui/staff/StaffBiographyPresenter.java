package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Staff;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffBiographyPresenter extends Presenter implements DataCallback<Staff> {

    private StaffBiographyFragment fragment;

    public StaffBiographyPresenter(StaffBiographyFragment fragment){
        this.fragment = fragment;
    }

    public void start(String slug){
//        repository.getStaffDetail(staffId,this);
    }

    @Override
    public void onBaseDataSuccess(Staff staff) {
//        fragment.onBaseDataSuccess(staff);
    }

    @Override
    public void onError(Throwable e) {

    }
}
