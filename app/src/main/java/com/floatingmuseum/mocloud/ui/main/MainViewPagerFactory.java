package com.floatingmuseum.mocloud.ui.main;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.ui.movie.MovieFragment;
import com.floatingmuseum.mocloud.ui.show.ShowFragment;
import com.floatingmuseum.mocloud.ui.user.UserFragment;

/**
 * Created by Floatingmuseum on 2017/7/19.
 */

class MainViewPagerFactory {

    static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieFragment();
                break;
            case 1:
                fragment = new ShowFragment();
                break;
            case 2:
                fragment = new UserFragment();
                break;
        }
        return fragment;
    }
}
