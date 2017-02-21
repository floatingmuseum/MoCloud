package com.floatingmuseum.mocloud.data;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.floatingmuseum.mocloud.data.bus.EventBusManager;
import com.floatingmuseum.mocloud.data.bus.SyncEvent;
import com.floatingmuseum.mocloud.data.callback.SyncCallback;
import com.floatingmuseum.mocloud.data.entity.LastActivities;
import com.floatingmuseum.mocloud.data.entity.MovieCollectionItem;
import com.floatingmuseum.mocloud.data.entity.MovieRatingItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchlistItem;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/2/16.
 */

public class SyncService extends Service implements SyncCallback {

    private Repository repository;
    private boolean hasFirstSync;
    private boolean isSyncing = false;
    private int syncSuccess = 0;
    //    private SyncManager syncManager;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = Repository.getInstance();
        hasFirstSync = SPUtil.getBoolean(SPUtil.SP_USER_LASTACTIVITIES, "has_first_sync", false);
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

    public void startSync() {
        if (isSyncing) {
            return;
        }
        isSyncing = true;
        if (hasFirstSync) {
            repository.getLastActivities(this);
        } else {
            syncAll();
        }
    }

    private void syncAll() {
        syncSuccess = 5;
        Logger.d("syncAll");
        repository.getLastActivities(this);
        repository.syncMovieWatched(this);//看过
        repository.syncMovieWatchlist(this);//想看
        repository.syncMovieRatings(this);//评分
        repository.syncMovieCollection(this);//拥有
    }

    private void syncNeeded(LastActivities lastActivities) {
        if (SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "all", "").equals(lastActivities.getAll())) {
            //没有变化，无需任何同步
            return;
        }
        SPUtil.editString(SPUtil.SP_USER_LASTACTIVITIES, "all", lastActivities.getAll());
        calculateSyncSuccess(lastActivities);
        if (syncSuccess == 0) {
            //0表示虽然有不同，但是下列需要同步选项不在其中
            EventBus.getDefault().post(new SyncEvent());
            stopSelf();
            return;
        }
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watched_at", "").equals(lastActivities.getMovies().getWatched_at())) {
            repository.syncMovieWatched(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watchlist_at", "").equals(lastActivities.getMovies().getWatchlisted_at())) {
            repository.syncMovieWatchlist(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_rated_at", "").equals(lastActivities.getMovies().getRated_at())) {
            repository.syncMovieRatings(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_collected_at", "").equals(lastActivities.getMovies().getCollected_at())) {
            repository.syncMovieCollection(this);
        }
    }

    private void calculateSyncSuccess(LastActivities lastActivities) {
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watched_at", "").equals(lastActivities.getMovies().getWatched_at())) {
            syncSuccess++;
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watchlisted_at", "").equals(lastActivities.getMovies().getWatchlisted_at())) {
            syncSuccess++;
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_rated_at", "").equals(lastActivities.getMovies().getRated_at())) {
            syncSuccess++;
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_collected_at", "").equals(lastActivities.getMovies().getCollected_at())) {
            syncSuccess++;
        }
    }

    @Override
    public void onLastActivitiesSucceed(LastActivities lastActivities) {
        if (hasFirstSync) {
            syncNeeded(lastActivities);
        } else {
            SPUtil.saveUserLastActivities(lastActivities);
            syncFinished();
        }
    }

    @Override
    public void onSyncMovieWatchedSucceed(List<MovieWatchedItem> movieWatchedItems) {
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
        syncFinished();
    }

    @Override
    public void onSyncMovieCollectionSucceed(List<MovieCollectionItem> movieCollectionItems) {
        syncFinished();
    }

    @Override
    public void onSyncMovieRatingsSucceed(List<MovieRatingItem> movieRatingItems) {
        syncFinished();
    }

    @Override
    public void onSyncMovieWatchlistSucceed(List<MovieWatchlistItem> movieWatchlistItems) {
        syncFinished();
    }

    private void syncFinished() {
        syncSuccess--;
        if (syncSuccess == 0) {
            EventBus.getDefault().post(new SyncEvent());
            stopSelf();
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("SyncService...Stop");
    }
}
