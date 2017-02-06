package com.floatingmuseum.mocloud.ui.staff;

import android.support.v4.app.Fragment;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffDetailFragmentFactory {
    public static Fragment create(int position, String slug){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = StaffBiographyFragment.newInstance(slug);
                break;
            case 1:
                fragment = StaffMoviesFragment.newInstance(slug);
                break;
            case 2:
                fragment = StaffShowsFragment.newInstance(slug);
                break;
        }
        return fragment;
    }
}
