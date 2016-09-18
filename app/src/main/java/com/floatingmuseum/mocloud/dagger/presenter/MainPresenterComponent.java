package com.floatingmuseum.mocloud.dagger.presenter;

import com.floatingmuseum.mocloud.dagger.ActivityScope;
import com.floatingmuseum.mocloud.dagger.repo.RepoComponent;
import com.floatingmuseum.mocloud.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Floatingmuseum on 2016/9/18.
 */
@ActivityScope
@Component(dependencies = RepoComponent.class,modules = MainPresenterModule.class)
public interface MainPresenterComponent {
    void inject(MainActivity mainActivity);
}
