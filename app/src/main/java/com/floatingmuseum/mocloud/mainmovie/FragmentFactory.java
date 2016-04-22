package com.floatingmuseum.mocloud.mainmovie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.floatingmuseum.mocloud.mainmovie.Trending.TrendingPresenter;
import com.floatingmuseum.mocloud.mainmovie.view.MovieAnticipatedFragment;
import com.floatingmuseum.mocloud.mainmovie.view.MovieBoxOfficeFragment;
import com.floatingmuseum.mocloud.mainmovie.view.MovieCollectedFragment;
import com.floatingmuseum.mocloud.mainmovie.view.MoviePlayedFragment;
import com.floatingmuseum.mocloud.mainmovie.view.MoviePopularFragment;
import com.floatingmuseum.mocloud.mainmovie.Trending.MovieTrendingFragment;
import com.floatingmuseum.mocloud.mainmovie.view.MovieWatchedFragment;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class FragmentFactory {
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
