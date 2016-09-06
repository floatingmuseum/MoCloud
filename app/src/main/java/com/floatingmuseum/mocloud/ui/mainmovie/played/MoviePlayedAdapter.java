package com.floatingmuseum.mocloud.ui.mainmovie.played;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MoviePlayedAdapter extends BaseQuickAdapter<BaseMovie>{

    public MoviePlayedAdapter(List<BaseMovie> data){
        super(R.layout.item_movie_trending,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
//        holder.setText(R.id.tv_title,movie.getTitle());
        ImageLoader.load(mContext,movie.getImages().getPoster().getMedium(),(RatioImageView)holder.getView(R.id.iv_poster));
    }
}
