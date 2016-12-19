package com.floatingmuseum.mocloud.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Floatingmuseum on 2016/12/19.
 */

public class Presenter {

    protected CompositeSubscription compositeSubscription = new CompositeSubscription();

    public void unSubscription(){
        compositeSubscription.unsubscribe();
    }
}
