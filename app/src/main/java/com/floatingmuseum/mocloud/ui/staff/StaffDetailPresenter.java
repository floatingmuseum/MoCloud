package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class StaffDetailPresenter implements DataCallback<Person> {
    private StaffDetailActivity activity;
    private Repository repository;

    public StaffDetailPresenter(StaffDetailActivity activity, Repository repository) {
        this.activity = activity;
        this.repository = repository;
    }

    public void getData(String id) {
        repository.getStaffDetail(id,this);
    }

    @Override
    public void onBaseDataSuccess(Person person) {
        activity.onBaseDataSuccess(person);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
    }
}
