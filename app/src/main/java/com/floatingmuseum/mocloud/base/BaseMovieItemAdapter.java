package com.floatingmuseum.mocloud.base;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
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
        Logger.d("MovieName:" + movie.getTitle() + "..." + image);
        if (image.getLocalImageUri() != null) {
            ImageLoader.load(mContext, image.getLocalImageUri(), posterView, R.drawable.default_movie_poster);
            Logger.d("图片从本地加载:" + movie.getTitle() + "...Uri:" + image.getLocalImageUri());
        } else if (image.getRemoteImageUrl() != null) {
            ImageLoader.load(mContext, image.getRemoteImageUrl(), posterView, R.drawable.default_movie_poster);
            Logger.d("图片从网络加载:" + movie.getTitle() + "..." + image.getTmdbID() + "...tmdbPosterUrl:" + image.getRemoteImageUrl());
        }
        Logger.d("没有图片showImage:" + movie.getTitle());
        ImageLoader.loadDefault(mContext, posterView);
    }

    protected void showTitle(TextView titleView, Movie movie) {
        titleView.setVisibility(View.GONE);
        ArtImage image = movie.getImage();
        if (image.getLocalImageUri() == null && image.getRemoteImageUrl() == null) {
            Logger.d("没有图片showTitle:" + movie.getTitle());
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(movie.getTitle());
        }
    }
}
