package com.floatingmuseum.mocloud.data;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.floatingmuseum.mocloud.data.callback.SyncCallback;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/2/16.
 */

public class SyncService extends Service implements SyncCallback{

    private Repository repository;
    private boolean hasFirstSync;
    //    private SyncManager syncManager;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = Repository.getInstance();
        hasFirstSync = SPUtil.getBoolean(SPUtil.SP_USER_LASTACTIVITIES,"has_first_sync",false);
//        syncManager = new SyncManager();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startSync();
        return super.onStartCommand(intent, flags, startId);
    }

    public void startSync(){
        if (hasFirstSync) {
            repository.getLastActivities(this);
        }else{
            syncAll();
        }
    }

    private void syncAll() {
        syncMovieCollection();
        syncMovieHistory();
        syncMovieRatings();
        syncMovieWatchlist();
    }

    private void syncNeeded() {

    }

    private void syncMovieCollection() {
    }

    private void syncMovieHistory() {
        repository.syncMovieHistory(this);
    }

    private void syncMovieRatings() {
    }

    private void syncMovieWatchlist() {
    }

    @Override
    public void onSyncMovieHistorySucceed(List<MovieWatchedItem> movieWatchedItems) {
//        if (movieWatchedItems!=null && movieWatchedItems.size()>0){
//            int count = 0;
//            for (MovieWatchedItem movieWatchedItem : movieWatchedItems) {
//                if (count==10){
//                    break;
//                }
//                Logger.d("Title:"+movieWatchedItem.getMovie().getTitle()+"...lastWatchedAt:"+movieWatchedItem.getLast_watched_at());
//                count++;
//            }
//        }
//        stopSelf();
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
