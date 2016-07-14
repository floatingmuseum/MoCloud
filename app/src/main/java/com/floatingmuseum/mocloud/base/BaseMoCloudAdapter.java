package com.floatingmuseum.mocloud.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.ViewGroup;

/**
 * Created by Floatingmuseum on 2016/7/13.
 */
public abstract class BaseMoCloudAdapter<T> extends Adapter {

    public BaseMoCloudAdapter(int resId,T data){

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
