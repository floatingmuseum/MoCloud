package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class MovieCollectionItem extends BaseMovieSyncItem {
    private String collected_at;

    public String getCollected_at() {
        return collected_at;
    }

    public void setCollected_at(String collected_at) {
        this.collected_at = collected_at;
    }
}
