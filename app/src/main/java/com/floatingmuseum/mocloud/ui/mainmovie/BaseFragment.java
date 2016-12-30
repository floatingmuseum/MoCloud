package com.floatingmuseum.mocloud.ui.mainmovie;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import com.floatingmuseum.mocloud.MoCloud;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public abstract class BaseFragment extends Fragment {

    protected Context context;
    protected MoCloud moCloud;
    protected boolean alreadyGetAllData = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Activity activity = (Activity) context;
        moCloud = (MoCloud) activity.getApplication();
    }

    protected void stopRefresh(SwipeRefreshLayout srl){
//        firstSeeLastItem = true;
        if(srl!=null){
            srl.setRefreshing(false);
        }
    }

    abstract protected void requestBaseData();
}
