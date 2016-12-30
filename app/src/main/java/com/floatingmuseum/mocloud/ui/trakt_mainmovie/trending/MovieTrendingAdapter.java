package com.floatingmuseum.mocloud.ui.trakt_mainmovie.trending;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/4/22.
 */
public class MovieTrendingAdapter extends BaseMovieItemAdapter<BaseMovie> {

    public MovieTrendingAdapter(List<BaseMovie> data) {
        super(R.layout.item_movie_trending, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        loadPoster((RatioImageView) holder.getView(R.id.iv_poster),movie);
        showTitle((TextView) holder.getView(R.id.tv_title),movie);
    }
}
