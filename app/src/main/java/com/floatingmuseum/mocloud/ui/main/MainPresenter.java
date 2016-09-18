package com.floatingmuseum.mocloud.ui.main;

import android.support.annotation.NonNull;

import com.floatingmuseum.mocloud.data.Repository;

import javax.inject.Inject;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
public class MainPresenter {

    MainActivity mainActivity;
    Repository repository;

    @Inject
    public MainPresenter(@NonNull MainActivity mainActivity, @NonNull Repository repository){
        this.mainActivity = mainActivity;
        this.repository = repository;
    }

    public void start(){

    }
}
