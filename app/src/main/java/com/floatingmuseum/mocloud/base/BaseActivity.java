package com.floatingmuseum.mocloud.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.MoCloud;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.dagger.repo.RepoComponent;
import com.floatingmuseum.mocloud.ui.comments.CommentsContract;
import com.orhanobut.logger.Logger;

/**
 * Created by Floatingmuseum on 2016/8/31.
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected AppBarLayout appBarLayout;
    protected Toolbar toolbar;
    protected boolean alreadyGetAllData = false;
    protected boolean firstSeeLastItem = true;
    protected boolean notFirstLoadData = false;
    protected boolean shouldClean = true;
    protected ActionBar actionBar;

    abstract protected  int currentLayoutId();
    abstract protected void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(currentLayoutId());
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        if(canGoBack()){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setOnClickListener(this);
    }

    protected boolean canGoBack(){
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    protected RepoComponent getRepoComponent(){
        return ((MoCloud)getApplication()).getRepoComponent();
    }

    /**
     * 加载更多
     * 条件：
     */
    protected void loadMore(LinearLayoutManager manager, BaseQuickAdapter adapter, String movieId,CommentsContract.Presenter presenter, SwipeRefreshLayout srl) {
        int lastItemPosition = manager.findLastVisibleItemPosition();
        if(lastItemPosition==(adapter.getItemCount()-4) && !alreadyGetAllData && firstSeeLastItem){
            firstSeeLastItem = false;
            presenter.start(movieId,false);
            shouldClean = false;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回键
        if(item.getItemId()==android.R.id.home){
            onBackPressed();
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }
}
