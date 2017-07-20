package com.floatingmuseum.mocloud.ui.tv.trending;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;

/**
 * Created by Floatingmuseum on 2017/7/20.
 */

public class TVTrendingFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tv_trending, container, false);
        return rootView;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void requestBaseData() {

    }
}
