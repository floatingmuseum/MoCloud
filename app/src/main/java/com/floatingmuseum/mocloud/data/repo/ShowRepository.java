package com.floatingmuseum.mocloud.data.repo;

import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseShow;
import com.floatingmuseum.mocloud.data.entity.Show;
import com.floatingmuseum.mocloud.data.net.MoCloudFactory;
import com.floatingmuseum.mocloud.data.net.MoCloudService;
import com.floatingmuseum.mocloud.utils.RxUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by Floatingmuseum on 2017/9/12.
 */

public class ShowRepository {

    private static ShowRepository repository;
    private MoCloudService service;

    private ShowRepository() {
        service = MoCloudFactory.getInstance();
    }

    public static ShowRepository getInstance() {
        if (repository == null) {
            synchronized (ShowRepository.class) {
                if (repository == null) {
                    repository = new ShowRepository();
                }
            }
        }
        return repository;
    }

    public void getShowsTrending(int pageNum, int limit, final DataCallback<List<BaseShow>> callback) {
        Logger.d("Service对象ShowRepository:" + service);
        service.getShowsTrending(pageNum, limit)
                .compose(RxUtil.<List<BaseShow>>observableTransformer())
                .subscribe(new Consumer<List<BaseShow>>() {
                    @Override
                    public void accept(List<BaseShow> baseShows) throws Exception {
                        Logger.d("getShowsTrending...onNext:" + baseShows);
                        callback.onBaseDataSuccess(baseShows);
                        for (BaseShow baseShow : baseShows) {
                            Show show = baseShow.getShow();
                            Logger.d("getShowsTrending...onNext...Show...Title:" + show.getTitle() + "...Overview:" + show.getOverview());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callback.onError(throwable);
                        Logger.d("getShowsTrending...onError");
                        throwable.printStackTrace();
                    }
                });
    }
}
