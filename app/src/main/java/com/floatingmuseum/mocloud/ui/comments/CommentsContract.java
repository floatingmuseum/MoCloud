package com.floatingmuseum.mocloud.ui.comments;

import com.floatingmuseum.mocloud.base.BaseView;
import com.floatingmuseum.mocloud.data.entity.Comment;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/9/2.
 */
public interface CommentsContract {
    interface View extends BaseView<Presenter> {
    }

    interface Presenter {
        void start(String movieId,boolean shouldClean);
        void onDestroy();
    }
}
