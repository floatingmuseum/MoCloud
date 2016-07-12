package com.floatingmuseum.mocloud;

import android.app.Application;
import android.content.Context;

import com.floatingmuseum.mocloud.dagger.repo.DaggerRepoComponent;
import com.floatingmuseum.mocloud.dagger.repo.RepoComponent;
import com.floatingmuseum.mocloud.dagger.repo.RepoModule;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoCloud extends Application {

    public static Context context;
    private RepoComponent repoComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        repoComponent = DaggerRepoComponent.builder().repoModule(new RepoModule()).build();
    }

    public RepoComponent getRepoComponent(){
        return repoComponent;
    }
}
