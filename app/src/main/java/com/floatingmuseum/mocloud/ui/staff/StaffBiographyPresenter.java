package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Person;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffBiographyPresenter extends Presenter implements DataCallback<Person> {

    private StaffBiographyFragment fragment;
    private Repository repository;

    public StaffBiographyPresenter(StaffBiographyFragment fragment, Repository repository){
        this.fragment = fragment;
        this.repository = repository;
    }

    public void start(String staffId){
        repository.getStaffDetail(staffId,this);
    }

    @Override
    public void onBaseDataSuccess(Person person) {
        fragment.onBaseDataSuccess(person);
    }

    @Override
    public void onError(Throwable e) {

    }
}
