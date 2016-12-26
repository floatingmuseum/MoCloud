package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffShowsPresenter extends Presenter {

    private StaffShowsFragment fragment;
    private Repository repository;

    public StaffShowsPresenter(StaffShowsFragment fragment, Repository repository){
        this.fragment = fragment;
        this.repository  = repository;
    }
}
