package com.floatingmuseum.mocloud.data.callback;


/**
 * Created by Floatingmuseum on 2016/8/15.
 */
public interface DataCallback<T extends Object> {
    void onBaseDataSuccess(T t);
    void onError(Throwable e);
}
