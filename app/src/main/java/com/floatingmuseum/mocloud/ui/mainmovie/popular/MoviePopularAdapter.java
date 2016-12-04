package com.floatingmuseum.mocloud.ui.mainmovie.popular;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularAdapter extends BaseMovieItemAdapter<Movie> {

    public MoviePopularAdapter(List<Movie> data){
        super(R.layout.item_movie_trending,data);
    }


    @Override
    protected void convert(BaseViewHolder holder, Movie movie) {
        loadPoster((RatioImageView) holder.getView(R.id.iv_poster),movie);
    }
}
