package com.floatingmuseum.mocloud;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.ui.mainmovie.nowplaying.MovieNowPlayingFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.popular.MoviePopularFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.toprated.MovieTopRatedFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.upcoming.MovieUpcomingFragment;


/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieFragmentFactory {
    public static Fragment create(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = MoviePopularFragment.newInstance();
//                fragment = MovieTrendingFragment.newInstance();
                break;
            case 1:
                fragment = MovieNowPlayingFragment.newInstance();
//                fragment = MoviePopularFragment.newInstance();
                break;
            case 2:
                fragment = MovieTopRatedFragment.newInstance();
//                fragment = MoviePlayedFragment.newInstance();
                break;
            case 3:
                fragment = MovieUpcomingFragment.newInstance();
//                fragment = MovieWatchedFragment.newInstance();
                break;
//            case 4:
//                fragment = MovieCollectedFragment.newInstance();
//                break;
////            case 5:
////                fragment = MovieAnticipatedFragment.newInstance();
////                break;
//            case 5:
//                fragment = MovieBoxOfficeFragment.newInstance();
//                break;
        }
        return fragment;
    }
}
