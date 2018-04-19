package com.floatingmuseum.mocloud;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.floatingmuseum.mocloud.data.Repository;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.github.moduth.blockcanary.BlockCanary;

import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoCloud extends MultiDexApplication {

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

        ButterKnife.setDebug(true);
    }

    private void initRealm() {
        Realm.init(this);

//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).withLimit(5000).build())
//                        .build());
    }
}
