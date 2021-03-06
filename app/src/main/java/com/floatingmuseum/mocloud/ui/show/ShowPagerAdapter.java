package com.floatingmuseum.mocloud.ui.show;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.utils.ResUtil;

/**
 * Created by Floatingmuseum on 2017/7/19.
 */

class ShowPagerAdapter extends FragmentPagerAdapter {

    ShowPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ShowPagerFactory.create(position);
    }

    @Override
    public int getCount() {
        return ResUtil.getStringArray(R.array.tab_tv_names).length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ResUtil.getStringArray(R.array.tab_tv_names)[position];
    }
}
