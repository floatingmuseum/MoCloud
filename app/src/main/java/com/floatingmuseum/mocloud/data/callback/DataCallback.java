package com.floatingmuseum.mocloud.data.callback;


/**
 * Created by yan on 2016/8/15.
 */
public interface DataCallback<T extends Object> {
    void onSuccess(T t);
    void onError(Throwable e);
}
