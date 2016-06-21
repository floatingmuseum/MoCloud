package com.floatingmuseum.mocloud;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.mainmovie.anticipated.MovieAnticipatedFragment;
import com.floatingmuseum.mocloud.mainmovie.boxoffice.MovieBoxOfficeFragment;
import com.floatingmuseum.mocloud.mainmovie.collected.MovieCollectedFragment;
import com.floatingmuseum.mocloud.mainmovie.played.MoviePlayedFragment;
import com.floatingmuseum.mocloud.mainmovie.popular.MoviePopularFragment;
import com.floatingmuseum.mocloud.mainmovie.trending.MovieTrendingFragment;
import com.floatingmuseum.mocloud.mainmovie.watched.MovieWatchedFragment;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MovieFragmentFactory {
    public static Fragment create(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = MovieTrendingFragment.newInstance();
                break;
            case 1:
                fragment = MoviePopularFragment.newInstance();
                break;
            case 2:
                fragment = MoviePlayedFragment.newInstance();
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
