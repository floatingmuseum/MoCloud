package com.floatingmuseum.mocloud.ui.mainmovie.popular;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.callback.DataCallback;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/1.
 */
public class MoviePopularAdapter extends BaseQuickAdapter<Movie>{

    public MoviePopularAdapter(List<Movie> data){
        super(R.layout.item_movie_trending,data);
    }


    @Override
    protected void convert(BaseViewHolder holder, Movie movie) {

        Logger.d("MovieName:" + movie.getTitle());
        if (movie.getImage() == null) {
            ImageLoader.load(mContext, null, (RatioImageView) holder.getView(R.id.iv_poster),
                    R.drawable.default_movie_poster);
            return;
        }
        String tmdbPosterUrl = "https://image.tmdb.org/t/p/w185"+movie.getImage().getPosters().get(0).getFile_path();
        Logger.d("tmdbPosterUrl:"+tmdbPosterUrl);
        ImageLoader.load(mContext,tmdbPosterUrl,(RatioImageView)holder.getView(R.id.iv_poster),
                R.drawable.default_movie_poster);
    }
}
