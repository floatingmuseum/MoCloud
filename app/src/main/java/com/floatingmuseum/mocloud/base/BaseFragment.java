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
import com.floatingmuseum.mocloud.ui.comments.CommentsContract;
import com.floatingmuseum.mocloud.ui.mainmovie.detail.MovieDetailActivity;
import com.floatingmuseum.mocloud.ui.mainmovie.trending.MovieTrendingContract;
import com.floatingmuseum.mocloud.ui.mainmovie.trending.MovieTrendingPresenter;
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
    protected boolean isFirstVisibleToUser = true;
    protected boolean isViewPrepared = false;
    protected boolean isVisibleToUser = false;

    abstract protected void initView();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        Activity activity = (Activity) context;
        moCloud = (MoCloud) activity.getApplication();
    }

    protected void loadMore(GridLayoutManager manager, BaseQuickAdapter adapter, BasePresenter presenter, SwipeRefreshLayout srl) {
        int lastItemPosition = manager.findLastVisibleItemPosition();
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
        if ((lastItemPosition+3)==adapter.getItemCount() && !srl.isRefreshing()){
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
        Logger.d("打开Activity...Movie:"+movie.getTitle()+"...TmdbID:"+movie.getIds().getTmdb());
        Intent intent = new Intent(context, MovieDetailActivity.class);
//        intent.putExtra(MovieDetailActivity.MOVIE_ID,movie.getIds().getSlug());
        intent.putExtra(MovieDetailActivity.MOVIE_OBJECT,movie);
//        intent.putExtra(MovieDetailActivity.MOVIE_TITLE,movie.getTitle());
        context.startActivity(intent);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d("isViewPrepared:"+isViewPrepared);
        this.isVisibleToUser = isVisibleToUser;
        if(isVisibleToUser && isFirstVisibleToUser && isViewPrepared){
            Logger.d("请求数据...setUserVisibleHint");
            isFirstVisibleToUser = false;
            requestBaseData();
        }
    }

    /**
     * 如果用户进入当前Fragment不是通过滑动页面，且当前Fragment不是之前Fragment的左右邻，
     * 则isViewPrepared会为false，导致第一次可见时不加载数据
     * @param srl
     * @param presenter
     */
    protected void requestBaseDataIfUserNotScrollToFragments(SwipeRefreshLayout srl,BasePresenter presenter){
        isViewPrepared = true;
        if (isViewPrepared && isFirstVisibleToUser && isVisibleToUser){
            isFirstVisibleToUser = false;
            Logger.d("请求数据...requestBaseDataIfUserNotScrollToFragments");
            srl.setRefreshing(true);
//        requestBaseData();
            presenter.start(true);
        }
    }

    abstract protected void requestBaseData();
}
