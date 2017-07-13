package com.floatingmuseum.mocloud.ui.mainmovie.anticipated;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.ArtImage;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieAnticipatedAdapter extends BaseMovieItemAdapter<BaseMovie, BaseViewHolder> {

    public MovieAnticipatedAdapter(List<BaseMovie> data) {
        super(R.layout.item_movie_anticipated, data);

    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        ArtImage image = movie.getImage();
        if (image.getLocalImageUri() != null) {
            ImageLoader.load(mContext, image.getLocalImageUri(), (ImageView) holder.getView(R.id.iv_poster), R.drawable.default_movie_poster);
            holder.setVisible(R.id.tv_title, false);
            Logger.d("图片从本地加载:" + movie.getTitle() + "...Uri:" + image.getLocalImageUri());
        } else if (image.getRemoteImageUrl() != null) {
            ImageLoader.load(mContext, image.getRemoteImageUrl(), (ImageView) holder.getView(R.id.iv_poster), R.drawable.default_movie_poster);
            holder.setVisible(R.id.tv_title, false);
            Logger.d("图片从网络加载:" + movie.getTitle() + "..." + image.getTmdbID() + "...tmdbPosterUrl:" + image.getRemoteImageUrl());
        } else {
            Logger.d("没有图片showImage:" + movie.getTitle());
            ImageLoader.loadDefault(mContext, (ImageView) holder.getView(R.id.iv_poster));
            holder.setVisible(R.id.tv_title, true).setText(R.id.tv_title, movie.getTitle());
        }
        holder.setText(R.id.tv_released_time, movie.getReleased());
    }
}
