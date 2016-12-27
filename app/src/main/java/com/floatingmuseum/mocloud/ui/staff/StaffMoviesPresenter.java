package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Staff;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffMoviesPresenter extends Presenter implements DataCallback<List<Staff>> {

    private StaffMoviesFragment fragment;
    private Repository repository;

    public StaffMoviesPresenter(StaffMoviesFragment fragment, Repository repository){
        this.fragment = fragment;
        this.repository = repository;
    }

    public void start(String staffId) {
        repository.getStaffMovieCredits(staffId,this);
    }

    @Override
    public void onBaseDataSuccess(List<Staff> works) {
        fragment.onBaseDataSuccess(works);
    }

    @Override
    public void onError(Throwable e) {

    }
}
