package com.floatingmuseum.mocloud.ui.recommendations;

import android.support.v7.widget.CardView;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.data.entity.TmdbMovieImage;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.NumberFormatUtil;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2017/1/16.
 */

public class RecommendationsAdapter extends BaseItemDraggableAdapter<Movie, BaseViewHolder> {

    public RecommendationsAdapter(List<Movie> data) {
        super(R.layout.item_recommendations, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, Movie movie) {
        baseViewHolder.setText(R.id.tv_movie_title, movie.getTitle())
                .setText(R.id.tv_released, movie.getReleased())
                .setText(R.id.tv_runtime, movie.getRuntime() + " mins")
                .setText(R.id.tv_language, movie.getLanguage())
                .setText(R.id.tv_rating, NumberFormatUtil.doubleFormatToString(movie.getRating(), false, 2) + "/" + movie.getVotes() + "votes");
        CardView cardView = baseViewHolder.getView(R.id.cardview);
//        cardView.setForeground(android.R.attr.selectableItemBackground);
        RatioImageView ivPoster = baseViewHolder.getView(R.id.iv_poster);
        ImageLoader.loadFromTmdbMovieImage(mContext, movie.getImage(), ivPoster, R.drawable.default_movie_poster);
    }
}
