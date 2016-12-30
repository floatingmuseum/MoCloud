package com.floatingmuseum.mocloud.ui.mainmovie.popular;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieDetail;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/12/30.
 */

public class MoviePopularAdapter extends BaseQuickAdapter<TmdbMovieDetail> {

    public MoviePopularAdapter(List<TmdbMovieDetail> data) {
        super(R.layout.item_movie_trending, data);
    }


    @Override
    protected void convert(BaseViewHolder holder, TmdbMovieDetail movie) {
        holder.setText(R.id.tv_title, movie.getTitle());
        ImageLoader.load(mContext, StringUtil.buildPosterUrl(movie.getPoster_path()), (RatioImageView) holder.getView(R.id.iv_poster), R.drawable.default_movie_poster);
    }
}
