package com.floatingmuseum.mocloud;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.ui.movie.anticipated.MovieAnticipatedFragment;
import com.floatingmuseum.mocloud.ui.movie.boxoffice.MovieBoxOfficeFragment;
import com.floatingmuseum.mocloud.ui.movie.collected.MovieCollectedFragment;
import com.floatingmuseum.mocloud.ui.movie.trending.MovieTrendingFragment;
import com.floatingmuseum.mocloud.ui.movie.watched.MovieWatchedFragment;


/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieFragmentFactory {
    public static Fragment create(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = MovieTrendingFragment.newInstance();
                break;
            case 1:
//                fragment = MoviePopularFragment.newInstance();
                fragment = MovieAnticipatedFragment.newInstance();
                break;
            case 2:
//                fragment = MoviePlayedFragment.newInstance();
                fragment = MovieBoxOfficeFragment.newInstance();
                break;
            case 3:
                fragment = MovieWatchedFragment.newInstance();
                break;
            case 4:
                fragment = MovieCollectedFragment.newInstance();
                break;
            case 5:
                fragment = MovieAnticipatedFragment.newInstance();
                break;
            case 6:
                fragment = MovieBoxOfficeFragment.newInstance();
                break;
        }
        return fragment;
    }
}
