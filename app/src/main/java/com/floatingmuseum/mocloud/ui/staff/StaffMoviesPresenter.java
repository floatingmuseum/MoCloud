package com.floatingmuseum.mocloud.ui.staff;

import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.callback.StaffWorksCallback;
import com.floatingmuseum.mocloud.data.entity.PeopleCredit;
import com.floatingmuseum.mocloud.data.entity.Staff;
import com.floatingmuseum.mocloud.utils.ListUtil;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffMoviesPresenter extends Presenter implements StaffWorksCallback<List<Staff>> {

    private StaffMoviesFragment fragment;
    private int pageNum = 1;
    private int limit = 10;
    private List<Staff> originalWorks = new ArrayList<>();
    private boolean isLoadMore;

    public StaffMoviesPresenter(StaffMoviesFragment fragment) {
        this.fragment = fragment;
    }

    public void start(int traktId, boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        if (isLoadMore) {
            pageNum++;
            getWorksImages();
        } else {
            pageNum = 1;
            compositeDisposable.add(repository.getStaffMovieCredits(traktId, this));
        }
    }

    @Override
    public void onBaseDataSuccess(List<Staff> works) {
        Logger.d("StaffMoviesPresenter...onBaseDataSuccess");
        originalWorks.clear();
        originalWorks.addAll(works);
        getWorksImages();
    }

    private void getWorksImages() {
        List<Staff> subWorks = ListUtil.subList(originalWorks, pageNum, limit);
        if (subWorks == null) {
            onGetWorksImagesSuccess(null);
            return;
        }
        for (Staff staff : subWorks) {
            Logger.d("StaffMoviesPresenter...Staff:" + staff.toString());
        }

        compositeDisposable.add(repository.getWorksImages(subWorks, this));
    }

    @Override
    public void onGetWorksImagesSuccess(List<Staff> staffs) {
        Logger.d("StaffMoviesPresenter...onGetWorksImagesSuccess:" + staffs);
        for (Staff staff : staffs) {
            Logger.d("StaffMoviesPresenter...Staff:" + staff.toString());
        }
        fragment.onGetWorksImagesSuccess(staffs, isLoadMore);
    }

    @Override
    public void onError(Throwable e) {

    }
}
