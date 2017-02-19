package com.floatingmuseum.mocloud.data.db;

import android.graphics.Movie;

import com.floatingmuseum.mocloud.data.db.entity.RealmMovieState;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;

import io.realm.Realm;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class RealmManager {

    private static Realm realm = Realm.getDefaultInstance();

    public static void insertOrUpdate(final MovieWatchedItem item){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmMovieState state = realm.createObject(RealmMovieState.class);
                state.setTitle(item.getMovie().getTitle());
                state.setYear(item.getMovie().getYear());
                state.setTrakt_id(item.getMovie().getIds().getTrakt());
                state.setTmdb_Id(item.getMovie().getIds().getTmdb());
                state.setImdb_id(item.getMovie().getIds().getImdb());
                state.setSlug(item.getMovie().getIds().getSlug());
                state.setPlays(item.getPlays());
                state.setLast_watched_at(item.getLast_watched_at());
            }
        });
    }

    public static void query(final MovieWatchedItem item){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmMovieState state = realm.where(RealmMovieState.class)
                        .equalTo("trakt_id",item.getMovie().getIds().getTrakt())
                        .findFirst();
            }
        });
    }
}
