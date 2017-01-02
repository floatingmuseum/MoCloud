package com.floatingmuseum.mocloud.base;

import com.floatingmuseum.mocloud.data.Repository;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Floatingmuseum on 2016/12/19.
 */

public abstract class Presenter {
    protected Repository repository = Repository.getInstance();

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public abstract void start(boolean shouldClean);
    public void unSubscription(){
        compositeSubscription.unsubscribe();
    }
}
