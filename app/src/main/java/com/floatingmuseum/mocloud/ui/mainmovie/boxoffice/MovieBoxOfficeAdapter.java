package com.floatingmuseum.mocloud.ui.mainmovie.boxoffice;

import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.base.BaseMovieItemAdapter;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.widgets.RatioImageView;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/6.
 */
public class MovieBoxOfficeAdapter extends BaseMovieItemAdapter<BaseMovie,BaseViewHolder> {

    public MovieBoxOfficeAdapter(List<BaseMovie> data){
        super(R.layout.item_movie_box_office,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();
        holder.setText(R.id.tv_box_movie_title,movie.getTitle());
        loadPoster((RatioImageView) holder.getView(R.id.iv_boxoffice_poster),movie);
    }
}
