package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffMoviesPresenter extends Presenter implements DataCallback<PeopleCredit> {

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
    public void onBaseDataSuccess(PeopleCredit peopleCredit) {
        fragment.onBaseDataSuccess(peopleCredit);
    }

    @Override
    public void onError(Throwable e) {

    }
}
