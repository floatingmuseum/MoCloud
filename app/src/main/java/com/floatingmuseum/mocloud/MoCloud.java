package com.floatingmuseum.mocloud;

import android.app.Application;
import android.content.Context;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoCloud extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Repository.init();
        ImageCacheManager.init();
        LeakCanary.install(this);
        BlockCanary.install(this,new AppBlockCanaryContext()).start();
    }
}
