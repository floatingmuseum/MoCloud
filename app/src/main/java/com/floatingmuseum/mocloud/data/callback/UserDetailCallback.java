package com.floatingmuseum.mocloud.data.callback;

import com.floatingmuseum.mocloud.data.entity.Follower;
import com.floatingmuseum.mocloud.data.entity.Stats;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/29.
 */

public interface UserDetailCallback {

    void onUserStatsSuccess(Stats stats);
    void onError(Exception e);
}
