package com.floatingmuseum.mocloud.ui.staff;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseFragment;
import com.orhanobut.logger.Logger;

/**
 * Created by Floatingmuseum on 2016/12/26.
 */

public class StaffShowsFragment extends BaseFragment {
    private String slug;

    public static Fragment newInstance(String slug) {
        StaffShowsFragment fragment = new StaffShowsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("slug",slug);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_staff_shows,container,false);

        initView();
        return view;
    }

    @Override
    protected void initView() {
        slug = getArguments().getString("slug");
        Logger.d("StaffID:"+slug);
    }

    @Override
    protected void requestBaseData() {

    }
}
