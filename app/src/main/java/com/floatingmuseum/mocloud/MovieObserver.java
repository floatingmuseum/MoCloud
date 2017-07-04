package com.floatingmuseum.mocloud;


import rx.Observer;

/**
 * Created by Floatingmuseum on 2017/7/4.
 */

public abstract class MovieObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {
    }
}
