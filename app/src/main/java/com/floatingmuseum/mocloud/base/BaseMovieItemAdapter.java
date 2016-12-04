package com.floatingmuseum.mocloud.base;

import android.net.Uri;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.net.ImageCacheManager;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/1.
 */

public abstract class BaseMovieItemAdapter<T extends Object> extends BaseQuickAdapter<T> {
    public BaseMovieItemAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    protected void loadPoster(RatioImageView posterView, Movie movie) {
        Logger.d("MovieName:" + movie.getTitle());
        if (movie.getImage().isHasCache()) {
            Uri fileUri = movie.getImage().getFileUri();
            ImageLoader.load(mContext, fileUri, posterView,
                    R.drawable.default_movie_poster);
            Logger.d("图片从本地加载:" + movie.getTitle());
            return;
        }
        Logger.d("图片从网络加载:" + movie.getTitle() + "..." + movie.getImage().getId());
        String tmdbPosterUrl = StringUtil.buildPosterUrl(movie.getImage().getPosters().get(0).getFile_path());
        Logger.d("tmdbPosterUrl:" + tmdbPosterUrl);
        ImageLoader.load(mContext, tmdbPosterUrl, posterView,
                R.drawable.default_movie_poster);
    }
}
