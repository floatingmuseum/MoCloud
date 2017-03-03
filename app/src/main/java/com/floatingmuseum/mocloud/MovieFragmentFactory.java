package com.floatingmuseum.mocloud;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.ui.mainmovie.anticipated.MovieAnticipatedFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.boxoffice.MovieBoxOfficeFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.collected.MovieCollectedFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.played.MoviePlayedFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.popular.MoviePopularFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.trending.MovieTrendingFragment;
import com.floatingmuseum.mocloud.ui.mainmovie.watched.MovieWatchedFragment;


/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieFragmentFactory {
    public static Fragment create(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
//                fragment = TmdbMoviePopularFragment.newInstance();
                fragment = MovieTrendingFragment.newInstance();
                break;
            case 1:
//                fragment = MovieNowPlayingFragment.newInstance();
                fragment = MoviePopularFragment.newInstance();
                break;
            case 2:
//                fragment = MovieTopRatedFragment.newInstance();
                fragment = MoviePlayedFragment.newInstance();
                break;
            case 3:
//                fragment = MovieUpcomingFragment.newInstance();
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
