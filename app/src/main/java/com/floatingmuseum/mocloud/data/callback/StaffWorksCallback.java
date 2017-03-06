package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Staff;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/3/6.
 */

public interface StaffWorksCallback<T> extends DataCallback<T>{

    void onGetWorksImagesSucceed(List<Staff> staffs);
}
