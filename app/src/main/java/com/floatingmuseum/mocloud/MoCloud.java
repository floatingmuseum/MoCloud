package com.floatingmuseum.mocloud;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.facebook.stetho.Stetho;
import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoCloud extends Application implements ThemeUtils.switchColor {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        Repository.init();
        ImageCacheManager.init(this);
//        LeakCanary.install(this);
        BlockCanary.install(this, new AppBlockCanaryContext()).start();
        initRealm();
    }

    private void initRealm() {
        Realm.init(this);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).withLimit(5000).build())
                        .build());
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
