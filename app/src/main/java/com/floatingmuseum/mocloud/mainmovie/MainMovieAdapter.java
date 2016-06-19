package com.floatingmuseum.mocloud.mainmovie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.floatingmuseum.mocloud.mainmovie.played.MoviePlayedFragment;
import com.floatingmuseum.mocloud.utils.ResUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by Floatingmuseum on 2016/4/22.
 */
public class MainMovieAdapter extends FragmentPagerAdapter {
    private FragmentManager fm;
    public MainMovieAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.create(position);
    }

    @Override
    public int getCount() {
        return ResUtil.getStringArray(R.array.tab_names).length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ResUtil.getStringArray(R.array.tab_names)[position];
    }
}
