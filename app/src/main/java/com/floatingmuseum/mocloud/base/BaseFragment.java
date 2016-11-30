package com.floatingmuseum.mocloud.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.ui.mainmovie.detail.MovieDetailActivity;
import com.orhanobut.logger.Logger;


/**
 * Created by Floatingmuseum on 2016/4/19.
 */
abstract public class BaseFragment extends Fragment {
    protected Context context;
    protected MoCloud moCloud;
    protected boolean alreadyGetAllData = false;
    protected boolean firstSeeLastItem = true;
    protected boolean notFirstLoadData = false;
    protected boolean isFirstVisibleToUser = false;
    protected boolean isViewPrepared = false;

    abstract protected void initView();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Activity activity = (Activity) context;
        moCloud = (MoCloud) activity.getApplication();
    }

    protected void loadMore(GridLayoutManager manager, BaseQuickAdapter adapter, BasePresenter presenter, SwipeRefreshLayout srl) {
        int lastItemPosition = manager.findLastCompletelyVisibleItemPosition();
//        Logger.d("lastItemPosition:"+lastItemPosition+"...可加载位置:"+(adapter.getItemCount()-4)+"...alreadyGetAllData:"+alreadyGetAllData+"...firstSeeLastItem:"+firstSeeLastItem+"...notFirstLoadData:"+notFirstLoadData);
//        if(lastItemPosition>(adapter.getItemCount()-4) && !alreadyGetAllData && firstSeeLastItem){
//            firstSeeLastItem = false;
//            presenter.start(false);
//            if(notFirstLoadData){
//                srl.setRefreshing(true);
//            }else{
//                notFirstLoadData = true;
//            }
//        }
        Logger.d("最后可见item:"+lastItemPosition+"...总条目数:"+adapter.getItemCount());
        if ((lastItemPosition+1)==adapter.getItemCount() && !srl.isRefreshing()){
            srl.setRefreshing(true);
            Logger.d("刷新...BaseFragment..."+srl);
            presenter.start(false);
        }
    }

    protected void stopRefresh(SwipeRefreshLayout srl){
//        firstSeeLastItem = true;
        if(srl!=null){
            srl.setRefreshing(false);
        }
    }

    protected void openMovieDetailActivity(Movie movie){
        Intent intent = new Intent(context, MovieDetailActivity.class);
//        intent.putExtra(MovieDetailActivity.MOVIE_ID,movie.getIds().getSlug());
        intent.putExtra(MovieDetailActivity.MOVIE_OBJECT,movie);
//        intent.putExtra(MovieDetailActivity.MOVIE_TITLE,movie.getTitle());
        context.startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && !isFirstVisibleToUser && isViewPrepared){
            isFirstVisibleToUser = true;
            requestBaseData();
        }
    }

    abstract protected void requestBaseData();
}
