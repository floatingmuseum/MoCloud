package com.floatingmuseum.mocloud.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class BaseFragment extends Fragment {
    protected Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
