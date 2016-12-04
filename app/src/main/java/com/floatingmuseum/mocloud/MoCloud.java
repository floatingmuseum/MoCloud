package com.floatingmuseum.mocloud;

import android.app.Application;
import android.content.Context;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.github.moduth.blockcanary.BlockCanary;

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
        BlockCanary.install(this,new AppBlockCanaryContext()).start();
    }
}
