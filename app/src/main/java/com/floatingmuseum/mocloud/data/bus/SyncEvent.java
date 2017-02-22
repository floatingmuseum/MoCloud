package com.floatingmuseum.mocloud.data.bus;

/**
 * Created by Floatingmuseum on 2017/2/22.
 */

public class SyncEvent {
    public String syncInfo;
    public boolean syncFinished;

    public SyncEvent(String syncInfo, boolean syncFinished) {
        this.syncInfo = syncInfo;
        this.syncFinished = syncFinished;
    }
}
