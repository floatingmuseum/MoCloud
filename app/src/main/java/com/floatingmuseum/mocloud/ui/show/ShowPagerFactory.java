package com.floatingmuseum.mocloud.ui.show;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.ui.show.anticipated.ShowAnticipatedFragment;
import com.floatingmuseum.mocloud.ui.show.trending.ShowTrendingFragment;

/**
 * Created by Floatingmuseum on 2017/7/19.
 */

class ShowPagerFactory {

    static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ShowTrendingFragment();
                break;
            case 1:
                fragment = new ShowAnticipatedFragment();
                break;
        }
        return fragment;
    }
}
