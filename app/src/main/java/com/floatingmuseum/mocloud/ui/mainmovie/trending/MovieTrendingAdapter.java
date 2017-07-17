package com.floatingmuseum.mocloud.ui.mainmovie.trending;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/4/22.
 */
class MovieTrendingAdapter extends BaseMovieItemAdapter<BaseMovie, BaseViewHolder> {

    MovieTrendingAdapter(List<BaseMovie> data) {
        super(R.layout.item_movie_trending, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        Logger.d("图片显示...trending..."+movie.getTitle()+"..."+movie.getImage().toString());
        loadPoster((RatioImageView) holder.getView(R.id.iv_poster), movie);
        showTitle((TextView) holder.getView(R.id.tv_title), movie);
        holder.setText(R.id.tv_people_watching, baseMovie.getWatchers()+" watching");
    }
}
