package com.floatingmuseum.mocloud.ui.movie.anticipated;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseItemAdapter;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class AnticipatedAdapter extends BaseItemAdapter<BaseMovie, BaseViewHolder> {

    public AnticipatedAdapter(List<BaseMovie> data) {
        super(R.layout.item_movie_anticipated, data);

    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        Logger.d("图片显示...anticipated..."+movie.getTitle()+"..."+movie.getImage().toString());

        loadPoster((RatioImageView) holder.getView(R.id.iv_poster),movie);
        showTitle((TextView) holder.getView(R.id.tv_title),movie);

        holder.setText(R.id.tv_released_time, movie.getReleased());
    }
}
