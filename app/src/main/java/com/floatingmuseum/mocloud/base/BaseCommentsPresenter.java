package com.floatingmuseum.mocloud.base;

import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.callback.CommentsCallback;
import com.floatingmuseum.mocloud.data.entity.Comment;
import com.floatingmuseum.mocloud.utils.SPUtil;
import com.floatingmuseum.mocloud.utils.ToastUtil;
import com.orhanobut.logger.Logger;

/**
 * Created by Floatingmuseum on 2017/3/3.
 */

public abstract class BaseCommentsPresenter extends Presenter {

    public void syncCommentLike(boolean isLike, Comment comment, CommentsCallback callback) {
        if (!SPUtil.isLogin()) {
            ToastUtil.showToast(R.string.not_login);
            return;
        }
        Logger.d("评论点赞测试:syncCommentLike...是否点赞:" + isLike);
        if (isLike) {
            repository.removeCommentFromLikes(comment, callback);
        } else {
            repository.addCommentToLikes(comment, callback);
        }
    }
}
