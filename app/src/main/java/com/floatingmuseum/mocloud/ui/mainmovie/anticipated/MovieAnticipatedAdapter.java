package com.floatingmuseum.mocloud.ui.mainmovie.anticipated;

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
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieAnticipatedAdapter extends BaseMovieItemAdapter<BaseMovie,BaseViewHolder> {

    public MovieAnticipatedAdapter(List<BaseMovie> data){
        super(R.layout.item_movie_trending,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
//        holder.setIsRecyclable(false);

//        RatioImageView iv_poster = holder.getView(R.id.iv_poster);
//        holder.setTag(iv_poster.getId(),movie.getIds().getTmdb());
//        TextView tv_title = holder.getView(R.id.tv_title);
//        holder.setTag(tv_title.getId(),movie.getIds().getTmdb());
//        iv_poster.getTag().equals(movie.getIds().getTmdb());
//        if (iv_poster.getTag()!=null && tv_title.getTag()!=null && iv_poster.getTag().equals(movie.getIds().getTmdb()) && tv_title.getTag().equals(movie.getIds().getTmdb())){
            Logger.d("没有图片convert:" + movie.getTitle());
            loadPoster((RatioImageView) holder.getView(R.id.iv_poster),movie);
            showTitle((TextView) holder.getView(R.id.tv_title),movie);
//        }
    }
}
