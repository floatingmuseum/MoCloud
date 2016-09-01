package com.floatingmuseum.mocloud.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.ui.mainmovie.detail.MovieDetailActivity;


/**
 * Created by Floatingmuseum on 2016/4/19.
 */
public class BaseFragment extends Fragment {
    protected Context context;
    protected MoCloud moCloud;
    protected boolean alreadyGetAllData = false;
    protected boolean firstSeeLastItem = true;
    protected boolean notFirstLoadData = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Activity activity = (Activity) context;
        moCloud = (MoCloud) activity.getApplication();
    }

    protected void loadMore(GridLayoutManager manager, BaseQuickAdapter adapter, BasePresenter presenter, SwipeRefreshLayout srl) {
        int lastItemPosition = manager.findLastVisibleItemPosition();
        if(lastItemPosition==(adapter.getItemCount()-4) && !alreadyGetAllData && firstSeeLastItem){
            firstSeeLastItem = false;
            presenter.start(false);
            if(notFirstLoadData){
                srl.setRefreshing(true);
            }else{
                notFirstLoadData = true;
            }
        }
    }

    protected void stopRefresh(SwipeRefreshLayout srl){
        firstSeeLastItem = true;
        if(srl!=null){
            srl.setRefreshing(false);
        }
    }

    protected void openMovieDetailActivity(String movieId){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_ID,movieId);
        context.startActivity(intent);
    }
}
