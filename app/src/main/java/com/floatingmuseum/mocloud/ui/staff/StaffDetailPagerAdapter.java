package com.floatingmuseum.mocloud.ui.staff;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.utils.ResUtil;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffDetailPagerAdapter extends FragmentPagerAdapter {

    private String staffId;

    public StaffDetailPagerAdapter(FragmentManager fm, String staffId) {
        super(fm);
        this.staffId = staffId;
    }

    @Override
    public Fragment getItem(int position) {
        return StaffDetailFragmentFactory.create(position,staffId);
    }

    @Override
    public int getCount() {
        return ResUtil.getStringArray(R.array.staff_tab_names).length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ResUtil.getStringArray(R.array.staff_tab_names)[position];
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
