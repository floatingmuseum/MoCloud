package com.floatingmuseum.mocloud.utils;


import com.floatingmuseum.mocloud.data.db.RealmManager;
import com.floatingmuseum.mocloud.data.db.entity.RealmCommentLike;
import com.floatingmuseum.mocloud.data.entity.Comment;

import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Floatingmuseum on 2016/12/15.
 */

public class RxUtil {

    private static Consumer<List<Comment>> CommentsResultLikesUpdateAction1 = new Consumer<List<Comment>>() {
        @Override
        public void accept(List<Comment> comments) throws Exception {
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

    /**
     * 更新评论数据的每条like状态
     */
    public static Consumer<List<Comment>> updateCommentsResultLikesState() {
        return CommentsResultLikesUpdateAction1;
    }

    public static <T> ObservableTransformer<T, T> observableTransformer() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> tObservable) {
                return tObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> SingleTransformer<T, T> singleTransformer() {
        return new SingleTransformer<T, T>() {
            @Override
            public SingleSource<T> apply(Single<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> FlowableTransformer<T, T> flowableTransformer() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
