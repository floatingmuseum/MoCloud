package com.floatingmuseum.mocloud.utils;

import android.Manifest;

import com.floatingmuseum.mocloud.data.db.RealmManager;
import com.floatingmuseum.mocloud.data.db.entity.RealmCommentLike;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDataList;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Floatingmuseum on 2016/12/15.
 */

public class RxUtil {

    private static Observable.Transformer schedulerTransFormer = new Observable.Transformer() {
        @Override
        public Observable call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    private static Func1<TmdbMovieDataList, TmdbMovieDataList> checkLocalPosterCache = new Func1<TmdbMovieDataList, TmdbMovieDataList>() {
        @Override
        public TmdbMovieDataList call(TmdbMovieDataList tmdbMovieDataList) {
            if (!PermissionsUtil.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Logger.d("没有读写权限，不下载");
                return tmdbMovieDataList;
            }
//            List<TmdbMovieDetail> movies = tmdbMovieDataList.getResults();
//            for (TmdbMovieDetail movie : movies) {
//                File file = ImageCacheManager.hasCacheImage(movie.getId(), ImageCacheManager.TYPE_POSTER);
//                if (file != null) {
//                    movie.setImageCacheFile(file);
//                }
//            }
            return tmdbMovieDataList;
        }
    };

    private static Action1<List<Comment>> CommentsResultLikesUpdateAction1 = new Action1<List<Comment>>() {
        @Override
        public void call(List<Comment> comments) {
            if (SPUtil.isLogin() && SPUtil.getBoolean(SPUtil.SP_USER_LASTACTIVITIES, "has_first_sync", false) && ListUtil.hasData(comments)) {
                for (Comment comment : comments) {
                    RealmCommentLike realmCommentLike = RealmManager.query(RealmCommentLike.class, "id", comment.getId());
                    if (realmCommentLike != null) {
                        comment.setLike(true);
                    }
                }
            }
        }
    };

    public static Action1<List<Comment>> updateCommentsResultLikesState() {
        return CommentsResultLikesUpdateAction1;
    }

    public static Func1 checkLocalPosterCache() {
        return checkLocalPosterCache;
    }

    public static <T> Observable.Transformer<T, T> threadSwitch() {
        return (Observable.Transformer<T, T>) schedulerTransFormer;
    }
}
