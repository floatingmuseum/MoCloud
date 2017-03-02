package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class MovieWatchlistItem extends BaseMovieSyncItem {
    private int rank;
    private String listed_at;
    private String type;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getListed_at() {
        return listed_at;
    }

    public void setListed_at(String listed_at) {
        this.listed_at = listed_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
