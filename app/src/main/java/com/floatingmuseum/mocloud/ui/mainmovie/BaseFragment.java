package com.floatingmuseum.mocloud.ui.mainmovie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.base.ListPresenter;
import com.floatingmuseum.mocloud.base.Presenter;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.ui.mainmovie.detail.MovieDetailActivity;

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

    protected void loadMore(GridLayoutManager manager, BaseQuickAdapter adapter, ListPresenter presenter, SwipeRefreshLayout srl) {
        int lastItemPosition = manager.findLastVisibleItemPosition();
//        Logger.d("最后可见item:"+lastItemPosition+"...总条目数:"+adapter.getItemCount());
        if ((lastItemPosition + 3) == adapter.getItemCount() && !srl.isRefreshing()) {
            srl.setRefreshing(true);
//            Logger.d("刷新...BaseFragment..."+srl);
            presenter.start(false);
        }
    }

    protected void openMovieDetailActivity(TmdbMovieDetail detail){
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(MovieDetailActivity.MOVIE_OBJECT,detail);
        context.startActivity(intent);
    }

    protected void stopRefresh(SwipeRefreshLayout srl) {
//        firstSeeLastItem = true;
        if (srl != null) {
            srl.setRefreshing(false);
        }
    }

    abstract protected void requestBaseData();
}
