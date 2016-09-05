package com.floatingmuseum.mocloud.ui.mainmovie.trending;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseumon 2016/4/22.
 */
public class MovieTrendingAdapter extends BaseQuickAdapter<BaseMovie> {

    public MovieTrendingAdapter(List<BaseMovie> data) {
        super(R.layout.item_movie_trending,data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        baseViewHolder.setText(R.id.tv_title,movie.getTitle());
        ImageLoader.load(mContext,movie.getImages().getPoster().getMedium(),(RatioImageView)baseViewHolder.getView(R.id.iv_poster));
    }
}
