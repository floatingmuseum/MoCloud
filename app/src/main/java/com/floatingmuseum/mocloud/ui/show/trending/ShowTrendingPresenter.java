package com.floatingmuseum.mocloud.ui.show.trending;

import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.data.repo.ShowRepository;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseShow;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/9/12.
 */

public class ShowTrendingPresenter extends ListPresenter implements DataCallback<List<BaseShow>> {

    private ShowTrendingFragment fragment;

    public ShowTrendingPresenter(ShowTrendingFragment fragment) {
        this.fragment=fragment;
    }

    public void start(boolean shouldClean) {
        ShowRepository.getInstance().getShowsTrending(getPageNum(shouldClean), limit, this);
//        repository.getShowsTrending(getPageNum(shouldClean), limit, this);
    }

    public int getLimit() {
        return limit;
    }

    @Override
    public void onBaseDataSuccess(List<BaseShow> data) {
        fragment.refreshData(data, shouldClean);
        //请求成功后，页码永久+1
        if (!shouldClean) {
            pageNum += 1;
        }
    }

    @Override
    public void onError(Throwable e) {
        fragment.onError();
        Logger.d("onError");
        e.printStackTrace();
    }
}
