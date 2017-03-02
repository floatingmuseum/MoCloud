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
import com.floatingmuseum.mocloud.data.entity.UserCommentLike;
import com.floatingmuseum.mocloud.data.entity.UserListLike;
import com.floatingmuseum.mocloud.data.entity.UserSettings;
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
    private int syncSuccessNeeded = 0;
    private LastActivities lastActivities;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = Repository.getInstance();
        hasFirstSync = SPUtil.getBoolean(SPUtil.SP_USER_LASTACTIVITIES, "has_first_sync", false);
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
        Logger.d("数据同步:hasFirstSync:" + hasFirstSync + "...isSyncing:" + isSyncing);
        if (isSyncing) {
            return;
        }
        isSyncing = true;
        if (hasFirstSync) {
            syncNeeded();
        } else {
            syncAll();
        }
    }

    private void syncAll() {
        syncSuccessNeeded = 8;
        Logger.d("syncAll");
        repository.getLastActivities(this);
        repository.syncUserSettings(this);
        repository.syncMovieWatched(this);//看过
        repository.syncMovieWatchlist(this);//想看
        repository.syncMovieRatings(this);//评分
        repository.syncMovieCollection(this);//拥有
        repository.syncUserCommentsLikes(this);//喜欢的评论
        repository.syncUserListsLikes(this);//喜欢的列表
    }

    private void syncNeeded() {
        syncSuccessNeeded = 2;
        repository.getLastActivities(this);
        repository.syncUserSettings(this);
    }

    @Override
    public void onLastActivitiesSucceed(LastActivities lastActivities) {
        Logger.d("Sync数据:onLastActivitiesSucceed");
        if (hasFirstSync) {
            syncOthers(lastActivities);
        } else {
            SPUtil.saveUserLastActivities(lastActivities);
            syncFinished("Sync last activities finished.");
        }
    }

    private void syncOthers(LastActivities lastActivities) {
        this.lastActivities = lastActivities;
        Logger.d("Sync数据:LastActivities...all:" + SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "all", "") + "...all from cloud:" + lastActivities.getAll());
        if (SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "all", "").equals(lastActivities.getAll())) {
            syncFinished("Sync last activities finished.");
            //没有变化，无需任何同步
            return;
        }
        calculateSyncNeeded(lastActivities);
        if (syncSuccessNeeded == 0) {
            //0表示虽然有不同，但是下列需要同步选项不在其中,修改一下SP中all的时间
            updateLastActivities("all", lastActivities.getAll());
            syncFinished("Sync last activities finished.");
            return;
        }
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watched_at", "").equals(lastActivities.getMovies().getWatched_at())) {
            Logger.d("Sync数据:syncMovieWatched");
            repository.syncMovieWatched(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watchlist_at", "").equals(lastActivities.getMovies().getWatchlisted_at())) {
            Logger.d("Sync数据:syncMovieWatchlist");
            repository.syncMovieWatchlist(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_rated_at", "").equals(lastActivities.getMovies().getRated_at())) {
            Logger.d("Sync数据:syncMovieRatings");
            repository.syncMovieRatings(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_collected_at", "").equals(lastActivities.getMovies().getCollected_at())) {
            Logger.d("Sync数据:syncMovieCollection");
            repository.syncMovieCollection(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "comments_liked_at", "").equals(lastActivities.getComments().getLiked_at())) {
            Logger.d("Sync数据:syncUserCommentsLikes");
            repository.syncUserCommentsLikes(this);
        }

        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "lists_liked_at", "").equals(lastActivities.getLists().getLiked_at())) {
            Logger.d("Sync数据:syncUserListsLikes");
            repository.syncUserListsLikes(this);
        }
    }

    /**
     * 计算有几项需要同步
     */
    private void calculateSyncNeeded(LastActivities lastActivities) {
        Logger.d("Sync数据:LastActivities...movies_watched_at:" + SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watched_at", "") + "...movies_watched_at from cloud:" + lastActivities.getMovies().getWatched_at());
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watched_at", "").equals(lastActivities.getMovies().getWatched_at())) {
            syncSuccessNeeded++;
        }
        Logger.d("Sync数据:LastActivities...movies_watchlist_at:" + SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watchlist_at", "") + "...movies_watchlist_at from cloud:" + lastActivities.getMovies().getWatchlisted_at());
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_watchlist_at", "").equals(lastActivities.getMovies().getWatchlisted_at())) {
            syncSuccessNeeded++;
        }
        Logger.d("Sync数据:LastActivities...movies_rated_at:" + SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_rated_at", "") + "...movies_rated_at from cloud:" + lastActivities.getMovies().getRated_at());
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_rated_at", "").equals(lastActivities.getMovies().getRated_at())) {
            syncSuccessNeeded++;
        }
        Logger.d("Sync数据:LastActivities...movies_collected_at:" + SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_collected_at", "") + "...movies_collected_at from cloud:" + lastActivities.getMovies().getCollected_at());
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "movies_collected_at", "").equals(lastActivities.getMovies().getCollected_at())) {
            syncSuccessNeeded++;
        }
        Logger.d("Sync数据:LastActivities...comments_liked_at:" + SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "comments_liked_at", "") + "...comments_liked_at from cloud:" + lastActivities.getComments().getLiked_at());
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "comments_liked_at", "").equals(lastActivities.getComments().getLiked_at())) {
            syncSuccessNeeded++;
        }
        Logger.d("Sync数据:LastActivities...lists_liked_at:" + SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "lists_liked_at", "") + "...lists_liked_at from cloud:" + lastActivities.getLists().getLiked_at());
        if (!SPUtil.getString(SPUtil.SP_USER_LASTACTIVITIES, "lists_liked_at", "").equals(lastActivities.getLists().getLiked_at())) {
            syncSuccessNeeded++;
        }
    }

    @Override
    public void onSyncUserSettingsSucceed(UserSettings userSettings) {
        SPUtil.saveUserSettings(userSettings);
        Logger.d("Sync数据:onSyncUserSettingsSucceed");
        syncFinished("Sync user settings finished.");
    }

    @Override
    public void onSyncMovieWatchedSucceed(List<MovieWatchedItem> movieWatchedItems) {
        Logger.d("Sync数据:onSyncMovieWatchedSucceed");
        if (hasFirstSync) {
            updateLastActivities("movies_watched_at", lastActivities.getMovies().getWatched_at());
        }
        syncFinished("Sync movie watched history finished.");
    }

    @Override
    public void onSyncMovieCollectionSucceed(List<MovieCollectionItem> movieCollectionItems) {
        Logger.d("Sync数据:onSyncMovieCollectionSucceed");
        if (hasFirstSync) {
            updateLastActivities("movies_collected_at", lastActivities.getMovies().getCollected_at());
        }
        syncFinished("Sync movie collection finished.");
    }

    @Override
    public void onSyncMovieRatingsSucceed(List<MovieRatingItem> movieRatingItems) {
        Logger.d("Sync数据:onSyncMovieRatingsSucceed");
        if (hasFirstSync) {
            updateLastActivities("movies_rated_at", lastActivities.getMovies().getRated_at());
        }
        syncFinished("Sync movie ratings finished");
    }

    @Override
    public void onSyncMovieWatchlistSucceed(List<MovieWatchlistItem> movieWatchlistItems) {
        Logger.d("Sync数据:onSyncMovieWatchlistSucceed");
        if (hasFirstSync) {
            updateLastActivities("movies_watchlist_at", lastActivities.getMovies().getWatchlisted_at());
        }
        syncFinished("Sync movie watchlist finished.");
    }

    @Override
    public void onSyncUserListLikesSucceed(List<UserListLike> userListLikes) {
        Logger.d("Sync数据:onSyncUserListLikesSucceed");
        if (hasFirstSync) {
            updateLastActivities("lists_liked_at", lastActivities.getLists().getLiked_at());
        }
        syncFinished("Sync user list likes finished");
    }

    @Override
    public void onSyncUserCommentsLikesSucceed(List<UserCommentLike> userCommentLikes) {
        Logger.d("Sync数据:onSyncUserCommentsLikesSucceed");
        if (hasFirstSync) {
            updateLastActivities("comments_liked_at", lastActivities.getComments().getLiked_at());
        }
        syncFinished("Sync user comment likes finished");
    }

    private void updateLastActivities(String key, String value) {
        SPUtil.editString(SPUtil.SP_USER_LASTACTIVITIES, key, value);
    }

    private void syncFinished(String syncInfo) {
        syncSuccessNeeded--;
        EventBus.getDefault().post(new SyncEvent(syncInfo, false));
        if (syncSuccessNeeded == 0) {
            EventBus.getDefault().post(new SyncEvent("All data sync finished.", true));

            if (!hasFirstSync) {//第一次同步成功，修改标记，之后再同步时只需要同步needed
                SPUtil.editBoolean(SPUtil.SP_USER_LASTACTIVITIES, "has_first_sync", true);
            }
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
