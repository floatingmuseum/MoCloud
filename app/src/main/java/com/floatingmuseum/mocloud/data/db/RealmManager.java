package com.floatingmuseum.mocloud.data.db;


import com.floatingmuseum.mocloud.data.db.entity.RealmCommentLike;
import com.floatingmuseum.mocloud.data.db.entity.RealmListLike;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieCollection;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieRating;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatched;
import com.floatingmuseum.mocloud.data.db.entity.RealmMovieWatchlist;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.CustomListInfo;
import com.floatingmuseum.mocloud.data.entity.MovieCollectionItem;
import com.floatingmuseum.mocloud.data.entity.MovieRatingItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchedItem;
import com.floatingmuseum.mocloud.data.entity.MovieWatchlistItem;
import com.floatingmuseum.mocloud.data.entity.User;
import com.floatingmuseum.mocloud.data.entity.UserCommentLike;
import com.floatingmuseum.mocloud.data.entity.UserListLike;
import com.floatingmuseum.mocloud.utils.ListUtil;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;

/**
 * Created by Floatingmuseum on 2017/2/14.
 */

public class RealmManager {

    public static void insertOrUpdateUserCommentsLikes(final List<UserCommentLike> userCommentsLikes) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(RealmCommentLike.class);
                        for (UserCommentLike item : userCommentsLikes) {
                            Comment comment = item.getComment();
                            User user = comment.getUser();
                            RealmCommentLike realmCommentLike = new RealmCommentLike();
                            realmCommentLike.setLiked_at(item.getLiked_at());
                            realmCommentLike.setId(comment.getId());
                            realmCommentLike.setParent_id(comment.getParent_id());
                            realmCommentLike.setCreated_at(comment.getCreated_at());
                            realmCommentLike.setUpdated_at(comment.getUpdated_at());
                            realmCommentLike.setComment(comment.getComment());
                            realmCommentLike.setSpoiler(comment.isSpoiler());
                            realmCommentLike.setReview(comment.isReview());
                            realmCommentLike.setReplies(comment.getReplies());
                            realmCommentLike.setLikes(comment.getLikes());
                            realmCommentLike.setUser_rating(comment.getUser_rating());
                            realmCommentLike.setUsername(user.getUsername());

                            realmCommentLike.setPrivate_user(user.isPrivateX());
                            realmCommentLike.setUser_slug(user.getIds().getSlug());
                            realmCommentLike.setVip(user.isVip());
                            realmCommentLike.setVip_ep(user.isVip_ep());
                            realmCommentLike.setName(user.getName());
                            realm.insertOrUpdate(realmCommentLike);
                        }
                    }
                });
    }

    public static void insertOrUpdateUserListsLikes(final List<UserListLike> userListsLikes) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(RealmListLike.class);
                        for (UserListLike item : userListsLikes) {
                            RealmListLike realmListLike = new RealmListLike();
                            CustomListInfo listInfo = item.getList();
                            User user = item.getUser();

                            realmListLike.setLiked_at(item.getLiked_at());
                            realmListLike.setList_name(listInfo.getName());
                            realmListLike.setDescription(listInfo.getDescription());
                            realmListLike.setPrivacy(listInfo.getPrivacy());
                            realmListLike.setDisplay_numbers(listInfo.isDisplay_numbers());
                            realmListLike.setAllow_comments(listInfo.isAllow_comments());
                            realmListLike.setUpdated_at(listInfo.getUpdated_at());
                            realmListLike.setItem_count(listInfo.getItem_count());
                            realmListLike.setComment_count(listInfo.getComment_count());
                            realmListLike.setLikes(listInfo.getLikes());
                            realmListLike.setId(listInfo.getIds().getTrakt());
                            realmListLike.setSlug(listInfo.getIds().getSlug());

                            realmListLike.setUsername(user.getUsername());
                            realmListLike.setPrivate_user(user.isPrivateX());
                            realmListLike.setUser_slug(user.getIds().getSlug());
                            realmListLike.setVip(user.isVip());
                            realmListLike.setVip_ep(user.isVip_ep());
                            realmListLike.setName(user.getName());
                            realm.insertOrUpdate(realmListLike);
                        }
                    }
                });
    }

    public static void insertOrUpdateMovieWatched(final List<MovieWatchedItem> movieWatchedItems) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(RealmMovieWatched.class);
                        for (MovieWatchedItem item : movieWatchedItems) {
                            RealmMovieWatched realmMovieWatched = new RealmMovieWatched();
                            realmMovieWatched.setTrakt_id(item.getMovie().getIds().getTrakt());
                            realmMovieWatched.setYear(item.getMovie().getYear());
                            realmMovieWatched.setTitle(item.getMovie().getTitle());
                            realmMovieWatched.setLast_watched_at(item.getLast_watched_at());
                            realmMovieWatched.setPlays(item.getPlays());
                            realm.insertOrUpdate(realmMovieWatched);
                        }
                    }
                });
    }

    public static void insertOrUpdateMovieWatchlist(final List<MovieWatchlistItem> movieWatchlistItems) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(RealmMovieWatchlist.class);
                        for (MovieWatchlistItem item : movieWatchlistItems) {
                            RealmMovieWatchlist realmMovieWatchlist = new RealmMovieWatchlist();
                            realmMovieWatchlist.setTrakt_id(item.getMovie().getIds().getTrakt());
                            realmMovieWatchlist.setYear(item.getMovie().getYear());
                            realmMovieWatchlist.setTitle(item.getMovie().getTitle());
                            realmMovieWatchlist.setListed_at(item.getListed_at());
                            realmMovieWatchlist.setRank(item.getRank());
                            realm.insertOrUpdate(realmMovieWatchlist);
                        }
                    }
                });
    }

    public static void insertOrUpdateMovieCollection(final List<MovieCollectionItem> movieCollectionItems) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(RealmMovieCollection.class);
                        for (MovieCollectionItem item : movieCollectionItems) {
                            RealmMovieCollection realmMovieCollection = new RealmMovieCollection();
                            realmMovieCollection.setTrakt_id(item.getMovie().getIds().getTrakt());
                            realmMovieCollection.setYear(item.getMovie().getYear());
                            realmMovieCollection.setTitle(item.getMovie().getTitle());
                            realmMovieCollection.setCollected_at(item.getCollected_at());
                            realm.insertOrUpdate(realmMovieCollection);
                        }
                    }
                });
    }

    public static void insertOrUpdateMovieRating(final List<MovieRatingItem> movieRatingItems) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(RealmMovieRating.class);
                        for (MovieRatingItem item : movieRatingItems) {
                            RealmMovieRating realmMovieRating = new RealmMovieRating();
                            realmMovieRating.setTrakt_id(item.getMovie().getIds().getTrakt());
                            realmMovieRating.setYear(item.getMovie().getYear());
                            realmMovieRating.setTitle(item.getMovie().getTitle());
                            realmMovieRating.setRating(item.getRating());
                            realmMovieRating.setRated_at(item.getRated_at());
                            realm.insertOrUpdate(realmMovieRating);
                        }
                    }
                });
    }

    public static void insertOrUpdate(final RealmModel model) {
        Realm.getDefaultInstance()
                .executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(model);
                    }
                });
    }

    public static void delete(final Class<? extends RealmModel> clazz, String filedName, long value) {
        RealmResults results = Realm.getDefaultInstance()
                .where(clazz)
                .equalTo(filedName, value)
                .findAll();
        if (ListUtil.hasData(results)) {
            results.deleteFirstFromRealm();
        }
    }

    public static <T extends RealmModel> T query(final Class<? extends RealmModel> clazz, String filedName, final long value) {
        RealmModel model = Realm.getDefaultInstance()
                .where(clazz)
                .equalTo(filedName, value)
                .findFirst();
        return (T) model;
    }
}
