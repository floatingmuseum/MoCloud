package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.Person;
import com.floatingmuseum.mocloud.data.entity.TmdbStaff;

import rx.Subscription;

/**
 * Created by Floatingmuseum on 2016/12/6.
 */

public class StaffDetailPresenter extends Presenter implements DataCallback<Person> {
    private StaffDetailActivity activity;

    public StaffDetailPresenter(StaffDetailActivity activity) {
        this.activity = activity;
    }

    public void getData(String traktId) {
        Subscription staffDetailSubscription = repository.getStaffDetail(traktId,this);
        repository.getStaffMovieCredits(traktId,this);
        compositeSubscription.add(staffDetailSubscription);
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
