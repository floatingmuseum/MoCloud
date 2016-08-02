package com.floatingmuseum.mocloud.dagger.repo;

import com.floatingmuseum.mocloud.data.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Floatingmuseum on 2016/7/11.
 */
@Module
public class RepoModule {

    @Singleton
    @Provides
    Repository provideRepository(){
        return new Repository();
    }
}
