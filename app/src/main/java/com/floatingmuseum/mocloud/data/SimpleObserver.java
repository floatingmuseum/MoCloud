package com.floatingmuseum.mocloud.data;


import rx.Observer;

/**
 * Created by Floatingmuseum on 2017/7/4.
 */

public abstract class SimpleObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {
    }
}
