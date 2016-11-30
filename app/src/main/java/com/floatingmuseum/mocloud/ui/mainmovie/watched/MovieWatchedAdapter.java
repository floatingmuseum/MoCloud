package com.floatingmuseum.mocloud.ui.mainmovie.watched;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.floatingmuseum.mocloud.R;
import com.floatingmuseum.mocloud.data.entity.BaseMovie;
import com.floatingmuseum.mocloud.data.entity.Movie;
import com.floatingmuseum.mocloud.utils.ImageLoader;
import com.floatingmuseum.mocloud.utils.StringUtil;
import com.floatingmuseum.mocloud.widgets.RatioImageView;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * Created by Floatingmuseum on 2016/5/5.
 */
public class MovieWatchedAdapter extends BaseQuickAdapter<BaseMovie>{

    public MovieWatchedAdapter(List<BaseMovie> data){
        super(R.layout.item_movie_trending,data);
    }

    @Override
    protected void convert(BaseViewHolder holder, BaseMovie baseMovie) {
        Movie movie = baseMovie.getMovie();

        Logger.d("MovieName:" + movie.getTitle());
        if (movie.getImage() == null) {
            ImageLoader.load(mContext, null, (RatioImageView) holder.getView(R.id.iv_poster),
                    R.drawable.default_movie_poster);
            return;
        }
        String tmdbPosterUrl = StringUtil.buildPosterUrl(movie.getImage().getPosters().get(0).getFile_path());
        Logger.d("tmdbPosterUrl:"+tmdbPosterUrl);
        ImageLoader.load(mContext,tmdbPosterUrl,(RatioImageView)holder.getView(R.id.iv_poster),
                R.drawable.default_movie_poster);

//        String fanartPosterUrl = movie.getImages().getMovieposter().get(0).getUrl();
//        fanartPosterUrl = fanartPosterUrl.replace("/fanart","/preview");
//        ImageLoader.load(mContext,fanartPosterUrl,(RatioImageView)holder.getView(R.id.iv_poster),
//                R.drawable.default_movie_poster);
    }
}
