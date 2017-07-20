package com.floatingmuseum.mocloud.ui.tv;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.ui.tv.anticipated.TVAnticipatedFragment;
import com.floatingmuseum.mocloud.ui.tv.trending.TVTrendingFragment;

/**
 * Created by Floatingmuseum on 2017/7/19.
 */

class TVPagerFactory {

    static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new TVTrendingFragment();
                break;
            case 1:
                fragment = new TVAnticipatedFragment();
                break;
        }
        return fragment;
    }
}
