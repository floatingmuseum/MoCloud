package com.floatingmuseum.mocloud.ui.staff;

import android.support.v4.app.Fragment;

import com.floatingmuseum.mocloud.data.entity.Staff;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffDetailFragmentFactory {
    public static Fragment create(int position, Staff staff){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = StaffBiographyFragment.newInstance(staff);
                break;
            case 1:
                fragment = StaffMoviesFragment.newInstance(staff);
                break;
            case 2:
                fragment = StaffShowsFragment.newInstance(staff);
                break;
        }
        return fragment;
    }
}
