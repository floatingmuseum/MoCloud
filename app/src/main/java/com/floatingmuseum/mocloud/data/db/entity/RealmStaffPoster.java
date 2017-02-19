package com.floatingmuseum.mocloud.data.db.entity;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/19.
 */
@RealmClass
public class RealmStaffPoster implements RealmModel{

    private int tmdb_id;
    private String storage_path;
}
