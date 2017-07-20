package com.floatingmuseum.mocloud.ui.movie.popular;


import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularAdapter extends BaseMovieItemAdapter<Movie,BaseViewHolder> {

    public MoviePopularAdapter(List<Movie> data){
        super(R.layout.item_movie_trending,data);
    }


    @Override
    protected void convert(BaseViewHolder holder, Movie movie) {
        loadPoster((RatioImageView) holder.getView(R.id.iv_poster),movie);
    }
}
