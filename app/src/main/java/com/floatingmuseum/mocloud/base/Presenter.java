package com.floatingmuseum.mocloud.base;

import com.floatingmuseum.mocloud.data.Repository;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Floatingmuseum on 2016/12/19.
 */

public abstract class Presenter {
    protected Repository repository = Repository.getInstance();
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();

//    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public void unSubscription() {
        compositeDisposable.dispose();
//        compositeSubscription.unsubscribe();
    }
}
