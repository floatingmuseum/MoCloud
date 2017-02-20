package com.floatingmuseum.mocloud.data.db.entity;


import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/**
 * Created by Floatingmuseum on 2017/2/19.
 */
@RealmClass
public class RealmMoviePoster implements RealmModel{

    @PrimaryKey
    private int tmdb_id;
    private String storage_path;
    private long last_use_time;
}
