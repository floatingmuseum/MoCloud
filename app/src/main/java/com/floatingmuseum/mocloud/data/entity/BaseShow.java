package com.floatingmuseum.mocloud.data.entity;

/**
 * Created by Floatingmuseum on 2017/9/12.
 */

public class BaseShow {
    private int watchers;
    private int list_count;
    private int watcher_count;
    private int play_count;
    private int collected_count;
    private int collector_count;
    private Show show;

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public int getList_count() {
        return list_count;
    }

    public void setList_count(int list_count) {
        this.list_count = list_count;
    }

    public int getWatcher_count() {
        return watcher_count;
    }

    public void setWatcher_count(int watcher_count) {
        this.watcher_count = watcher_count;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }

    public int getCollected_count() {
        return collected_count;
    }

    public void setCollected_count(int collected_count) {
        this.collected_count = collected_count;
    }

    public int getCollector_count() {
        return collector_count;
    }

    public void setCollector_count(int collector_count) {
        this.collector_count = collector_count;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }
}
