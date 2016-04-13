package com.floatingmuseum.mocloud;

import android.app.Application;
import android.content.Context;

/**
 * Created by Floatingmuseum on 2016/4/13.
 */
public class MoCloud extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
