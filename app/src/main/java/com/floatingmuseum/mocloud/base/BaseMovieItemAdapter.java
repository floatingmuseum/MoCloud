package com.floatingmuseum.mocloud.base;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/1.
 */

public abstract class BaseMovieItemAdapter<T extends Object, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> {
    public BaseMovieItemAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    protected void loadPoster(RatioImageView posterView, Movie movie) {
        ArtImage image = movie.getImage();
        ImageLoader.loadArtImage(mContext, image, posterView, ImageCacheManager.TYPE_POSTER);
    }

    protected void showTitle(TextView titleView, Movie movie) {
        titleView.setVisibility(View.GONE);
        ArtImage image = movie.getImage();
        if (image.getLocalPosterUri() == null && TextUtils.isEmpty(image.getRemotePosterUrl())) {
            Logger.d("没有图片showTitle:" + movie.getTitle());
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(movie.getTitle());
        }
    }
}
