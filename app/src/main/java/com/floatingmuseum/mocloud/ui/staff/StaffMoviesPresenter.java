package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.StaffWorksCallback;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffMoviesPresenter extends Presenter implements StaffWorksCallback<List<Staff>> {

    private StaffMoviesFragment fragment;
    private int imageRequestStart = 0;
    private int imageRequestEnd = 9;
    private List<Staff> originalWorks = new ArrayList<>();


    public StaffMoviesPresenter(StaffMoviesFragment fragment) {
        this.fragment = fragment;
    }

    public void start(String slug) {
        compositeSubscription.add(repository.getStaffMovieCredits(slug, this));
    }

    @Override
    public void onBaseDataSuccess(List<Staff> works) {
        Logger.d("StaffMoviesPresenter...onBaseDataSuccess");
        originalWorks.addAll(works);
        if (originalWorks.size() < 10) {
            imageRequestEnd = originalWorks.size() - 1;
        }
        getWorksImages();
    }

    public void getWorksImages() {
        compositeSubscription.add(repository.getWorksImages(originalWorks, imageRequestStart, imageRequestEnd, this));
    }

    @Override
    public void onGetWorksImagesSucceed(List<Staff> staffs) {
        Logger.d("StaffMoviesPresenter...onGetWorksImagesSucceed");
        boolean alreadyGetAllWorksImages = staffs.size() == originalWorks.size() || staffs.size() < 10;
        fragment.onGetWorksImagesSucceed(staffs, alreadyGetAllWorksImages);
    }

    @Override
    public void onError(Throwable e) {

    }
}
