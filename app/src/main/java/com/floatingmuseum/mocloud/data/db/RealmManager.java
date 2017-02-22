package com.floatingmuseum.mocloud.data.db;


import com.floatingmuseum.mocloud.data.db.entity.RealmMovieState;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatched;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatchlist;
import com.floatingmuseum.mocloud.data.entity.Ids;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.MovieCollectionItem;
import com.floatingmuseum.mocloud.data.entity.MovieRatingItem;
import com.floatingmuseum.mocloud.data.entity.MovieSyncItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchlistItem;
import com.orhanobut.logger.Logger;

import io.realm.Realm;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class RealmManager {


//    public static void insertOrUpdate(final RealmMovieWatched item) {
//        Realm.getDefaultInstance()
//                .executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        realm.insertOrUpdate(item);
//                    }
//                });
//    }
//
//    public static void insertOrUpdate(final RealmMovieWatchlist item) {
//        Realm.getDefaultInstance()
//                .executeTransaction(new Realm.Transaction() {
//                    @Override
//                    public void execute(Realm realm) {
//                        realm.insertOrUpdate(item);
//                    }
//                });
//    }

    public static void insertOrUpdate(final MovieSyncItem item) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int trakt = item.getMovie().getIds().getTrakt();
                        RealmMovieState state = realm.where(RealmMovieState.class).equalTo("trakt_id", trakt).findFirst();
                        if (state == null) {
                            Logger.d("Sync数据:插入数据:" + item.getMovie().getTitle());
                            insertMovieState(realm, item);
                        } else {
                            Logger.d("Sync数据:更新数据:" + item.getMovie().getTitle());
                            updateMovieState(state, item);
                        }
                    }
                });
    }

    private static void insertMovieState(Realm realm, MovieSyncItem item) {
        RealmMovieState state = new RealmMovieState();
        Movie movie = item.getMovie();
        state.setTrakt_id(movie.getIds().getTrakt());
        state.setTitle(movie.getTitle());
        state.setYear(movie.getYear());
        updateMovieState(state, item);
        realm.insert(state);
    }

    private static void updateMovieState(RealmMovieState state, MovieSyncItem item) {
        if (item instanceof MovieWatchedItem) {
            MovieWatchedItem movieWatchedItem = (MovieWatchedItem) item;
            state.setLast_watched_at(movieWatchedItem.getLast_watched_at());
            state.setPlays(movieWatchedItem.getPlays());
        } else if (item instanceof MovieWatchlistItem) {
            MovieWatchlistItem movieWatchlistItem = (MovieWatchlistItem) item;
            state.setListed_at(movieWatchlistItem.getListed_at());
            state.setRank(movieWatchlistItem.getRank());
        } else if (item instanceof MovieCollectionItem) {
            MovieCollectionItem movieCollectionItem = (MovieCollectionItem) item;
            state.setCollected_at(movieCollectionItem.getCollected_at());

        } else if (item instanceof MovieRatingItem) {
            MovieRatingItem movieRatingItem = (MovieRatingItem) item;
            state.setRated_at(movieRatingItem.getRated_at());
            state.setRating(movieRatingItem.getRating());
        }
    }

    public static void query(final MovieWatchedItem item) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        RealmMovieState state = realm.where(RealmMovieState.class)
                                .equalTo("trakt_id", item.getMovie().getIds().getTrakt())
                                .findFirst();
                    }
                });
    }
}
