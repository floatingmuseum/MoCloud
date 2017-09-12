package com.floatingmuseum.mocloud.ui.movie.played;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseItemAdapter;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class PlayedAdapter extends BaseItemAdapter<BaseMovie,BaseViewHolder> {

    public PlayedAdapter(List<BaseMovie> data){
        super(R.layout.item_movie_trending,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        loadPoster((RatioImageView) holder.getView(R.id.iv_poster),movie);
    }
}
