package com.floatingmuseum.mocloud.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.floatingmuseum.mocloud.MoCloud;

/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class BaseFragment extends Fragment {
    protected Context context;
    protected MoCloud moCloud;
    protected boolean alreadyGetAllData = false;
    protected boolean firstSeeLastItem = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Activity activity = (Activity) context;
        moCloud = (MoCloud) activity.getApplication();
    }

    protected void stopRefresh(SwipeRefreshLayout srl){
        firstSeeLastItem = true;
        if(srl!=null){
            srl.setRefreshing(false);
        }
    }
}
