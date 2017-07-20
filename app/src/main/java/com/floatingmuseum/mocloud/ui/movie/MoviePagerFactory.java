package com.floatingmuseum.mocloud.ui.movie;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.ui.movie.anticipated.MovieAnticipatedFragment;
import com.floatingmuseum.mocloud.ui.movie.boxoffice.MovieBoxOfficeFragment;
import com.floatingmuseum.mocloud.ui.movie.trending.MovieTrendingFragment;

/**
 * Created by Floatingmuseum on 2017/7/19.
 */

class MoviePagerFactory {

    static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MovieTrendingFragment();
                break;
            case 1:
                fragment = new MovieAnticipatedFragment();
                break;
            case 2:
                fragment = new MovieBoxOfficeFragment();
                break;
        }
        return fragment;
    }
}
