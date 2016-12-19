package com.floatingmuseum.mocloud.base;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.floatingmuseum.mocloud.R;
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

public abstract class BaseMovieItemAdapter<T extends Object> extends BaseQuickAdapter<T> {
    public BaseMovieItemAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    protected void loadPoster(RatioImageView posterView, Movie movie) {
        TmdbMovieImage image = movie.getImage();
        Logger.d("MovieName:" + movie.getTitle() + "..." + image);
        if (image != null) {
            if (image.isHasCache()) {
                File file = image.getCacheFile();
                ImageLoader.load(mContext, file, posterView, R.drawable.default_movie_poster);
                Logger.d("图片从本地加载:" + movie.getTitle() + "..." + file.getName());
                return;
            } else if (image.isHasPoster()) {
                String tmdbPosterUrl = StringUtil.buildPosterUrl(image.getPosters().get(0).getFile_path());
                ImageLoader.load(mContext, tmdbPosterUrl, posterView, R.drawable.default_movie_poster);
                Logger.d("图片从网络加载:" + movie.getTitle() + "..." + image.getId() + "...tmdbPosterUrl:" + tmdbPosterUrl);
                return;
            }
        }
        Logger.d("没有图片showImage:" + movie.getTitle());
        ImageLoader.loadDefault(mContext, posterView);
    }

    protected void showTitle(TextView titleView, Movie movie) {
        titleView.setVisibility(View.GONE);
        TmdbMovieImage image = movie.getImage();
        if (image == null || (!image.isHasPoster() & !image.isHasCache())) {
            Logger.d("没有图片showTitle:" + movie.getTitle());
            titleView.setVisibility(View.VISIBLE);
            titleView.setText(movie.getTitle());
        }
    }
}
