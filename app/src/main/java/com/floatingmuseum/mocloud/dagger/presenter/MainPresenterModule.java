package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.ui.main.MainActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */

@Module
public class MainPresenterModule {
    private MainActivity mainActivity;

    public MainPresenterModule(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Provides
    public MainActivity providesMainActivity(){
        return mainActivity;
    }
}
