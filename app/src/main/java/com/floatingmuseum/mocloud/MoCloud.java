package com.floatingmuseum.mocloud;

import android.app.Application;
import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoCloud extends Application implements ThemeUtils.switchColor{

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Repository.init();
//        ImageCacheManager.init();
        LeakCanary.install(this);
        BlockCanary.install(this,new AppBlockCanaryContext()).start();
    }

    @Override
    public int replaceColorById(Context context, @ColorRes int colorId) {
        return 0;
    }

    @Override
    public int replaceColor(Context context, @ColorInt int color) {
        return 0;
    }
}
