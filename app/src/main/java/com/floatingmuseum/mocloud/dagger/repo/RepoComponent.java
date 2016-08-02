package com.floatingmuseum.mocloud.dagger.repo;

import com.floatingmuseum.mocloud.data.Repository;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Floatingmuseum on 2016/7/11.
 */

@Singleton
@Component(modules = RepoModule.class)
public interface RepoComponent {
    Repository getRepository();
}
